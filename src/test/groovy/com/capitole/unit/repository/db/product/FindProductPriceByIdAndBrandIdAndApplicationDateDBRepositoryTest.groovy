package com.capitole.unit.repository.db.product

import com.capitole.core.entity.ProductPrice
import com.capitole.core.exception.RepositoryException
import com.capitole.core.exception.TooManyResultsException
import com.capitole.repository.db.product.FindProductPriceByIdAndBrandIdAndApplicationDateDBRepository
import org.springframework.jdbc.core.JdbcOperations
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType
import spock.lang.Specification

import javax.sql.DataSource
import java.time.LocalDateTime

class FindProductPriceByIdAndBrandIdAndApplicationDateDBRepositoryTest extends Specification {
    private static JdbcOperations jdbcOperations
    private static FindProductPriceByIdAndBrandIdAndApplicationDateDBRepository findProductByIdAndBrandIdAndApplicationDateDBRepository

    def setupSpec() {
        def dataSource = createDataSource()
        jdbcOperations = new JdbcTemplate(dataSource)
        findProductByIdAndBrandIdAndApplicationDateDBRepository = new FindProductPriceByIdAndBrandIdAndApplicationDateDBRepository(jdbcOperations)
    }

    def "Execute, when query execution was successfully and there is one result, expect Product found"() {
        given:
        Long productId = 35455
        Long brandId = 1
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T10:00:00")
        and:
        def productPriceExpected = createProductPrice(productId, brandId, "2020-06-14T00:00:00", "2020-12-31T23:59:59", 35.5, 1, 0)

        when:
        def result = findProductByIdAndBrandIdAndApplicationDateDBRepository.execute(productId, brandId, applicationDate)

        then: "Evaluate results"
        result.isPresent()
        result.get() == productPriceExpected
    }

    def "Execute, when query execution was successfully and there are two result, expect Product found with higher priority"() {
        given: "Input values"
        Long productId = 35455
        Long brandId = 1
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-14T16:00:00")
        and: "Expected product found with highest priority"
        def productPriceExpected = createProductPrice(productId, brandId, "2020-06-14T15:00:00", "2020-06-14T18:30:00", 25.45, 2)

        when:
        def result = findProductByIdAndBrandIdAndApplicationDateDBRepository.execute(productId, brandId, applicationDate)

        then: "Evaluate results"
        result.isPresent()
        result.get() == productPriceExpected
    }

    def "Execute, when execute query successfully, expect none Product was found"() {
        given:
        Long productId = 1
        Long brandId = 1
        LocalDateTime applicationDate = LocalDateTime.now()

        when:
        def result = findProductByIdAndBrandIdAndApplicationDateDBRepository.execute(productId, brandId, applicationDate)

        then: "Evaluate results"
        result.isEmpty()
    }

    def "Execute, when execute query fails, expect Repository exception"() {
        given:
        Long productId = 1
        Long brandId = 1
        LocalDateTime applicationDate = LocalDateTime.now()
        and:
        def jdbcOperation = Mock(JdbcOperations)
        def findProductByIdAndBrandIdAndApplicationDateDBRepository = new FindProductPriceByIdAndBrandIdAndApplicationDateDBRepository(jdbcOperation)
        jdbcOperation.query(_ as String, _ as RowMapper<ProductPrice>, _ as Object[]) >> { throw new RuntimeException("Unexpected error") }

        when:
        findProductByIdAndBrandIdAndApplicationDateDBRepository.execute(productId, brandId, applicationDate)

        then:
        RepositoryException err = thrown()
        err.errorCode == "repository.db.brand-product-prices-by-date"
        err.cause instanceof RuntimeException
    }

    def "Execute, when execute query successfully but cannot determine a unique result to retrieve, expect TooManyResults exception"() {
        given:
        Long productId = 35455
        Long brandId = 1
        LocalDateTime applicationDate = LocalDateTime.parse("2020-06-15T19:00:00")

        when:
        findProductByIdAndBrandIdAndApplicationDateDBRepository.execute(productId, brandId, applicationDate)

        then:
        TooManyResultsException err = thrown()
        err.errorCode == "repository.db.brand-product-prices-by-date.too-many-results"
    }

    private static ProductPrice createProductPrice(Long productId, Long brandId, String startDate, String endDate, BigDecimal price, Long listPriceId, Long priority = 1) {
        return ProductPrice.builder()
                .productId(productId)
                .brandId(brandId)
                .startDate(LocalDateTime.parse(startDate))
                .endDate(LocalDateTime.parse(endDate))
                .price(price)
                .listPriceId(listPriceId)
                .currency("EUR")
                .priority(priority)
                .build()
    }

    private static DataSource createDataSource() {
        return new EmbeddedDatabaseBuilder()
                .generateUniqueName(true)
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding("UTF-8")
                .addScript("db/unit/V202105170001__init_schema.sql")
                .addScript("db/unit/V202105170002__insert_values.sql")
                .build()
    }
}
