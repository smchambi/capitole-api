package com.capitole.repository.db.product;

import com.capitole.repository.db.common.QueryStatement;

/**
 * Queries used in DB to retrieve {@link com.capitole.core.entity.ProductPrice} results.
 */
enum ProductPriceDbQueryStatement implements QueryStatement {
	SELECT_BY_ID_AND_BRAND_ID_BETWEEN_APPLICATION_DATE("SELECT p.PRODUCT_ID, p.BRAND_ID, p.PRICE, p.PRICE_LIST, p.CURR, p.PRIORITY, p.START_DATE, p.END_DATE "
			+ "FROM PRICES as p "
			+ "WHERE p.PRODUCT_ID = ? AND p.BRAND_ID = ? AND ? BETWEEN p.START_DATE AND p.END_DATE "
			+ "ORDER BY p.PRIORITY DESC");

	private final String queryStatement;

	ProductPriceDbQueryStatement(String queryStatement) {
		this.queryStatement = queryStatement;
	}

	@Override
	public String get() {
		return queryStatement;
	}
}
