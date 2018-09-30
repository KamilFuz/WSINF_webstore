package com.webstore.repository;

import com.webstore.domain.CartItem;
import com.webstore.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CartItemRepository extends CrudRepository <CartItem, Long> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
