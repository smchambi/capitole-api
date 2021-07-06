package com.capitole.core.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class ProductPrice implements Entity {
	private final Long productId;
	private final Long brandId;
	private final LocalDateTime startDate;
	private final LocalDateTime endDate;
	private final BigDecimal price;
	private final Long listPriceId;
	private final String currency;
	private final Long priority;

	private ProductPrice(Long productId, Long brandId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal price, Long listPriceId, String currency, Long priority) {
		this.productId = productId;
		this.brandId = brandId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.price = price;
		this.listPriceId = listPriceId;
		this.currency = currency;
		this.priority = priority;
	}

	public static class ProductPriceBuilder {
		private Long productId;
		private Long brandId;
		private LocalDateTime startDate;
		private LocalDateTime endDate;
		private BigDecimal price;
		private Long listPriceId;
		private String currency;
		private Long priority;

		private ProductPriceBuilder() {
		}

		public ProductPriceBuilder productId(Long productId) {
			this.productId = productId;
			return this;
		}

		public ProductPriceBuilder brandId(Long brandId) {
			this.brandId = brandId;
			return this;
		}

		public ProductPriceBuilder startDate(LocalDateTime startDate) {
			this.startDate = startDate;
			return this;
		}

		public ProductPriceBuilder endDate(LocalDateTime endDate) {
			this.endDate = endDate;
			return this;
		}

		public ProductPriceBuilder price(BigDecimal price) {
			this.price = price;
			return this;
		}

		public ProductPriceBuilder listPriceId(Long listPriceId) {
			this.listPriceId = listPriceId;
			return this;
		}

		public ProductPriceBuilder currency(String currency) {
			this.currency = currency;
			return this;
		}

		public ProductPriceBuilder priority(Long priority) {
			this.priority = priority;
			return this;
		}

		public ProductPrice build() {
			return new ProductPrice(productId, brandId, startDate, endDate, price, listPriceId, currency, priority);
		}
	}

	public static ProductPriceBuilder builder() {
		return new ProductPriceBuilder();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProductPrice that = (ProductPrice) o;
		return productId.equals(that.productId) && brandId.equals(that.brandId) && startDate.equals(that.startDate) && endDate.equals(that.endDate) && price.equals(that.price) && listPriceId.equals(that.listPriceId) && currency.equals(that.currency) && priority.equals(that.priority);
	}

	@Override
	public int hashCode() {
		return Objects.hash(productId, brandId, startDate, endDate, price, listPriceId, currency, priority);
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

	public Long getListPrice() {
		return listPriceId;
	}

	public String getCurrency() {
		return currency;
	}

	public Long getPriority() {
		return priority;
	}
}
