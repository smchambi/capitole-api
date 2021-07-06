package com.capitole.repository.db.product;

import com.capitole.core.entity.ProductPrice;
import com.capitole.core.exception.RepositoryException;
import com.capitole.core.exception.TooManyResultsException;
import com.capitole.core.repository.FindProductPriceByIdAndBrandIdAndApplicationDateRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Repository;

@Repository
public class FindProductPriceByIdAndBrandIdAndApplicationDateDBRepository implements FindProductPriceByIdAndBrandIdAndApplicationDateRepository {
	private static final String REPOSITORY_ERROR_CODE = "repository.db.brand-product-prices-by-date";
	private static final String TO_MANY_RESULTS_ERROR_CODE = "repository.db.brand-product-prices-by-date.too-many-results";

	private static final String REPOSITORY_ERROR_MESSAGE = "Error when try to get price. Cause: %s";
	private static final String TO_MANY_RESULTS_ERROR_MESSAGE = "Unexpected too many results, cannot determine which get";
	private static final int SIZE_RESULTS_EXPECTED = 1;
	private final JdbcOperations jdbcOperations;

	public FindProductPriceByIdAndBrandIdAndApplicationDateDBRepository(final JdbcOperations jdbcOperations) {
		this.jdbcOperations = jdbcOperations;
	}

	@Override
	public Optional<ProductPrice> execute(Long productId, Long brandId, LocalDateTime applicationDate) {
		var allResults = retrieveProducts(productId, brandId, applicationDate);
		return getHighestPriorityProductPrice(allResults);
	}

	/**
	 * Get from sql db list of products.
	 *
	 * @param productId       query param
	 * @param brandId         query param
	 * @param applicationDate query param to filter results between start date and end date of {@link ProductPrice}
	 * @return Products prices filtered by input params ordered desc by priority ProductPrice field
	 */
	private List<ProductPrice> retrieveProducts(Long productId, Long brandId, LocalDateTime applicationDate) {
		try {
			return jdbcOperations.query(ProductPriceDbQueryStatement.SELECT_BY_ID_AND_BRAND_ID_BETWEEN_APPLICATION_DATE.get(),
					ResultDataToProductMapper::map,
					productId, brandId, applicationDate);
		} catch (Exception e) {
			throw new RepositoryException(REPOSITORY_ERROR_CODE, String.format(REPOSITORY_ERROR_MESSAGE, e.getMessage()), e);
		}
	}

	/**
	 * Get Highest product price from collection by priority.
	 *
	 * @param productPrices to be evaluate and retrieve the highest product price
	 * @return A unique or empty result
	 */
	private static Optional<ProductPrice> getHighestPriorityProductPrice(List<ProductPrice> productPrices) {
		if (productPrices.size() <= SIZE_RESULTS_EXPECTED) {
			return productPrices.stream().findFirst();
		}
		var firstPriorityProductPrice = productPrices.stream().findFirst().get();

		var cantProductPricesWithHigherPriority = productPrices.stream()
				.map(ProductPrice::getPriority)
				.filter(firstPriorityProductPrice.getPriority()::equals)
				.count();

		if (cantProductPricesWithHigherPriority > SIZE_RESULTS_EXPECTED) {
			throw new TooManyResultsException(TO_MANY_RESULTS_ERROR_CODE, TO_MANY_RESULTS_ERROR_MESSAGE);
		}
		return Optional.of(firstPriorityProductPrice);
	}
}
