package com.capitole.unit.core.usecase.product

import com.capitole.core.entity.ProductPrice
import com.capitole.core.exception.EntityNotFoundException
import com.capitole.core.exception.RepositoryException
import com.capitole.core.exception.UseCaseException
import com.capitole.core.exception.ValidationException
import com.capitole.core.repository.FindProductPriceByIdAndBrandIdAndApplicationDateRepository
import com.capitole.core.usecase.product.FindProductByBrandIdAndApplicationDate
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

class FindProductPriceByBrandIdAndApplicationDateTest extends Specification {
    private FindProductPriceByIdAndBrandIdAndApplicationDateRepository findProductByIdAndBrandIdAndApplicationDateRepository = Mock(FindProductPriceByIdAndBrandIdAndApplicationDateRepository)
    private FindProductByBrandIdAndApplicationDate usecase = new FindProductByBrandIdAndApplicationDate(findProductByIdAndBrandIdAndApplicationDateRepository)

    def "Execute, when input values are valid, expect product found"() {
        given: "Input data"
        Long productId = 1L
        Long brandId = 1L
        LocalDateTime applicationDate = LocalDateTime.now()
        def inputValues = createInputValue(productId, brandId, applicationDate)

        and: "Result data from repository"
        def productPriceExpected = ProductPrice.builder()
                .productId(productId)
                .brandId(brandId)
                .startDate(applicationDate.minusDays(1))
                .endDate(applicationDate.plusDays(1))
                .price(100.0)
                .currency("USD")
                .priority(1)
                .build()

        when: "Usecase was executed usecase"
        def result = usecase.execute(inputValues)

        then: "Expect one interaction with initial values"
        1 * findProductByIdAndBrandIdAndApplicationDateRepository.execute(productId, brandId, applicationDate) >> Optional.of(productPriceExpected)
        and: "Equals than result expected"
        productPriceExpected == result.getProduct()
    }

    @Unroll
    def "Execute, when #mandatoryField is not valid, expect ValidationException"() {
        given: "Input data"
        def inputValues = createInputValue(mandatoryField = null)

        when: "Usecase was executed usecase"
        usecase.execute(inputValues)

        then: "Get validation error"
        ValidationException error = thrown()
        error.errorCode == "mandatory.field"
        error.message == "Mandatory field is null or empty"

        then: "Expect one interaction with initial values"
        0 * findProductByIdAndBrandIdAndApplicationDateRepository.execute(_, _, _)

        where:
        mandatoryField << ["productId", "brandId", "applicationDate"]
    }

    def "Execute, when no result was found, expect UsecaseException with NotFoundException cause"() {
        given: "Input data"
        Long productId = 1L
        Long brandId = 1L
        LocalDateTime applicationDate = LocalDateTime.now()
        def inputValues = createInputValue(productId, brandId, applicationDate)

        when: "Usecase was executed usecase"
        usecase.execute(inputValues)

        then: "Get validation error"
        UseCaseException error = thrown()
        error.errorCode == "find-by-productPrice-id-brand-id-and-date"
        error.cause instanceof EntityNotFoundException
        (error.cause as EntityNotFoundException).errorCode == "not.found"

        then: "Expect one interaction with initial values and result is empty"
        1 * findProductByIdAndBrandIdAndApplicationDateRepository.execute(productId, brandId, applicationDate) >> Optional.empty()
    }

    def "Execute, when have an unexpected error in repository, expect UsecaseException with RepositoryException cause"() {
        given: "Input data"
        Long productId = 1L
        Long brandId = 1L
        LocalDateTime applicationDate = LocalDateTime.now()
        def inputValues = createInputValue(productId, brandId, applicationDate)
        and: "Prepare expected exception"
        def expectedError = new RepositoryException("error.code", "Error in repository", new RuntimeException())


        when: "Usecase was executed usecase"
        usecase.execute(inputValues)

        then: "Get validation error"
        UseCaseException error = thrown()
        error.errorCode == "find-by-productPrice-id-brand-id-and-date"
        error.cause == expectedError

        then: "Expect one interaction with initial values and result is empty"
        1 * findProductByIdAndBrandIdAndApplicationDateRepository.execute(productId, brandId, applicationDate) >> { throw expectedError }
    }

    private static createInputValue(Long productId = 1L, Long brandId = 1L, LocalDateTime applicationDate = LocalDateTime.now()) {
        return new FindProductByBrandIdAndApplicationDate.InputValues(brandId, productId, applicationDate)
    }
}
