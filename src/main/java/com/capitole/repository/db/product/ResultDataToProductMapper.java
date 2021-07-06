package com.capitole.repository.db.product;

import com.capitole.core.entity.ProductPrice;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

final class ResultDataToProductMapper {
	private static final String PRODUCT_ID_FIELD_NAME = "PRODUCT_ID";
	private static final String BRAND_ID_FIELD_NAME = "BRAND_ID";
	private static final String PRICE_FIELD_NAME = "PRICE";
	private static final String PRICE_LIST_FIELD_NAME = "PRICE_LIST";
	private static final String CURRENCY_FIELD_NAME = "CURR";
	private static final String PRIORITY_FIELD_NAME = "PRIORITY";
	private static final String START_DATE_FIELD_NAME = "START_DATE";
	private static final String END_DATE_FIELD_NAME = "END_DATE";

	private ResultDataToProductMapper() {
		//Empty constructor
	}

	/**
	 * To map result from {@link ProductPriceDbQueryStatement} queries.
	 */
	static ProductPrice map(ResultSet rs, int rowNum) throws SQLException {
		return ProductPrice.builder()
				.productId(rs.getLong(PRODUCT_ID_FIELD_NAME))
				.brandId(rs.getLong(BRAND_ID_FIELD_NAME))
				.startDate(rs.getObject(START_DATE_FIELD_NAME, LocalDateTime.class))
				.endDate(rs.getObject(END_DATE_FIELD_NAME, LocalDateTime.class))
				.price(rs.getBigDecimal(PRICE_FIELD_NAME))
				.listPriceId(rs.getLong(PRICE_LIST_FIELD_NAME))
				.priority(rs.getLong(PRIORITY_FIELD_NAME))
				.currency(rs.getString(CURRENCY_FIELD_NAME))
				.build();
	}
}
