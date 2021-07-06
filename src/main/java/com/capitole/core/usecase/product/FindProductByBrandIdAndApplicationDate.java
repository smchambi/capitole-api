package com.capitole.core.usecase.product;

import com.capitole.core.entity.ProductPrice;
import com.capitole.core.exception.EntityNotFoundException;
import com.capitole.core.exception.UseCaseException;
import com.capitole.core.repository.FindProductPriceByIdAndBrandIdAndApplicationDateRepository;
import java.time.LocalDateTime;

public class FindProductByBrandIdAndApplicationDate {
	private static final String NOT_FOUND_ERROR_MESSAGE = "There is no exist productPrice";
	// Error codes
	private static final String USE_CASE_ERROR_CODE = "find-by-productPrice-id-brand-id-and-date";

	private final FindProductPriceByIdAndBrandIdAndApplicationDateRepository findProductPriceByIdAndBrandIdAndApplicationDateRepository;

	public FindProductByBrandIdAndApplicationDate(FindProductPriceByIdAndBrandIdAndApplicationDateRepository findProductPriceByIdAndBrandIdAndApplicationDateRepository) {
		this.findProductPriceByIdAndBrandIdAndApplicationDateRepository = findProductPriceByIdAndBrandIdAndApplicationDateRepository;
	}

	public OutputValues execute(InputValues inputValues) {
		var productId = MandatoryFieldHelper.getValue(inputValues.getProductId());
		var brandId = MandatoryFieldHelper.getValue(inputValues.getBrandId());
		var applicationDate = MandatoryFieldHelper.getValue(inputValues.getApplicationDate());
		try {
			return findProductPriceByIdAndBrandIdAndApplicationDateRepository.execute(productId, brandId, applicationDate)
					.map(OutputValues::new)
					.orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_ERROR_MESSAGE));
		} catch (Exception e) {
			throw new UseCaseException(USE_CASE_ERROR_CODE, e.getMessage(), e);
		}
	}

	public static class InputValues {
		private final Long brandId;
		private final Long productId;
		private final LocalDateTime applicationDate;

		public InputValues(Long brandId, Long productId, LocalDateTime applicationDate) {
			this.brandId = brandId;
			this.productId = productId;
			this.applicationDate = applicationDate;
		}

		public Long getBrandId() {
			return brandId;
		}

		public Long getProductId() {
			return productId;
		}

		public LocalDateTime getApplicationDate() {
			return applicationDate;
		}
	}

	public static class OutputValues {
		private final ProductPrice productPrice;

		public OutputValues(ProductPrice productPrice) {
			this.productPrice = productPrice;
		}

		public ProductPrice getProduct() {
			return productPrice;
		}
	}
}
