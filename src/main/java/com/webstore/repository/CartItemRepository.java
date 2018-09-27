package com.webstore.repository;

import com.webstore.domain.CartItem;
import com.webstore.domain.ShoppingCart;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CartItemRepository extends CrudRepository <CartItem, Long> {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
