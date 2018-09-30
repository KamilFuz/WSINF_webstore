package com.webstore.repository;

import com.webstore.domain.CartItem;
import com.webstore.domain.ProductToCartItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductToCartItemRepository extends CrudRepository<ProductToCartItem, Long> {

    void deleteByCartItem(CartItem cartItem);
}
