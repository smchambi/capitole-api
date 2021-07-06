package com.capitole.entrypoint.response;

import com.capitole.core.entity.ProductPrice;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductPriceResponse {
	private final Long productId;
	private final Long brandId;
	private final LocalDateTime startDate;
	private final LocalDateTime endDate;
	private final BigDecimal price;
	private final Long listPriceId;
	private final String currency;

	public ProductPriceResponse(ProductPrice productPrice) {
		this.productId = productPrice.getProductId();
		this.brandId = productPrice.getBrandId();
		this.startDate = productPrice.getStartDate();
		this.endDate = productPrice.getEndDate();
		this.price = productPrice.getPrice();
		this.listPriceId = productPrice.getListPrice();
		this.currency = productPrice.getCurrency();
	}

	public Long getProductId() {
		return productId;
	}

	public Long getBrandId() {
		return brandId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public Long getListPriceId() {
		return listPriceId;
	}

	public String getCurrency() {
		return currency;
	}
}
