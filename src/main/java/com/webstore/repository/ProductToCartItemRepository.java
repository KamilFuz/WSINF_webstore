package com.webstore.repository;

import com.webstore.domain.ProductToCartItem;
import org.springframework.data.repository.CrudRepository;

public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long> {
}
