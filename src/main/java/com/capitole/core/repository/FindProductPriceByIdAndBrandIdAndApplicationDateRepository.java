package com.capitole.core.repository;

import com.capitole.core.entity.ProductPrice;
import java.time.LocalDateTime;
import java.util.Optional;

public interface FindProductPriceByIdAndBrandIdAndApplicationDateRepository {
	Optional<ProductPrice> execute(Long id, Long brandId, LocalDateTime applicationDate);
}
