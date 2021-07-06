package com.capitole.entrypoint;

import com.capitole.core.usecase.product.FindProductByBrandIdAndApplicationDate;
import com.capitole.entrypoint.response.ProductPriceResponse;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
	private final FindProductByBrandIdAndApplicationDate findProductByBrandIdAndApplicationDate;

	public ProductController(FindProductByBrandIdAndApplicationDate findProductByBrandIdAndApplicationDate) {
		this.findProductByBrandIdAndApplicationDate = findProductByBrandIdAndApplicationDate;
	}

	@GetMapping("/brands/{brandId}/products/{productId}")
	public ProductPriceResponse find(
			@PathVariable("brandId") Long brandId,
			@PathVariable("productId") Long productId,
			@RequestParam("application") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime application) {
		var input = new FindProductByBrandIdAndApplicationDate.InputValues(brandId, productId, application);
		var output = findProductByBrandIdAndApplicationDate.execute(input);
		return new ProductPriceResponse(output.getProduct());
	}
}
