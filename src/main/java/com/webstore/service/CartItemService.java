package com.webstore.service;

import com.webstore.domain.CartItem;
import com.webstore.domain.Product;
import com.webstore.domain.ShoppingCart;
import com.webstore.domain.User;

import java.util.List;

public interface CartItemService {
    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);

    CartItem updateCartItem(CartItem cartItem);

    CartItem addProductToCartItem(Product product, User user, int qty);
}
