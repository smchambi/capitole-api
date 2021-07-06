package com.capitole.integration.entrypoint

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerIntegrationTest extends Specification {
    private static final String URI_PRODUCTS = "/brands/%d/products/%d?application=%s"

    @Autowired
    private TestRestTemplate restTemplate

    @Unroll
    def "GET product price, when application date is #aplication for brand id 1 (Zara) and product id 35455, expect product price found 200-OK"() {
        given:
        Long productId = 35455L
        Long brandId = 1L
        def uri = String.format(URI_PRODUCTS, brandId, productId, aplication)
        and:
        def responseExpected = [product_id: productId, brand_id: brandId, start_date: startDateExpected, end_date: endDateExpected, price: priceExpected, list_price_id: priceListExpected, currency: "EUR"]

        when:
        def responseEntity = restTemplate.getForEntity(uri, HashMap.class)

        then:
        responseEntity.getStatusCode() == HttpStatus.OK
        responseEntity.getBody() == responseExpected

        where:
        aplication            | priceExpected | priceListExpected | startDateExpected     | endDateExpected
        "2020-06-14T10:00:00" | 35.5          | 1                 | "2020-06-14T00:00:00" | "2020-12-31T23:59:59"
        "2020-06-14T16:00:00" | 25.45         | 2                 | "2020-06-14T15:00:00" | "2020-06-14T18:30:00"
        "2020-06-14T21:00:00" | 35.5          | 1                 | "2020-06-14T00:00:00" | "2020-12-31T23:59:59"
        "2020-06-15T10:00:00" | 30.5          | 3                 | "2020-06-15T00:00:00" | "2020-06-15T11:00:00"
        "2020-06-16T21:00:00" | 38.95         | 4                 | "2020-06-15T16:00:00" | "2020-12-31T23:59:59"
    }
}
