package com.capitole.config.usecase.product;

import com.capitole.core.repository.FindProductPriceByIdAndBrandIdAndApplicationDateRepository;
import com.capitole.core.usecase.product.FindProductByBrandIdAndApplicationDate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductUseCaseConfig {
	@Bean
	protected FindProductByBrandIdAndApplicationDate findProductByBrandIdAndApplicationDate(
			final FindProductPriceByIdAndBrandIdAndApplicationDateRepository findProductPriceByIdAndBrandIdAndApplicationDateRepository) {
		return new FindProductByBrandIdAndApplicationDate(findProductPriceByIdAndBrandIdAndApplicationDateRepository);
	}
}
