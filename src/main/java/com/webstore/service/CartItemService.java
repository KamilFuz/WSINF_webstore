package com.webstore.service;

import com.webstore.domain.CartItem;
import com.webstore.domain.ShoppingCart;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
