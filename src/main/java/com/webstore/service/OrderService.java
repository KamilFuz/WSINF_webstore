package com.webstore.service;

import com.webstore.domain.*;

import java.util.Optional;

public interface OrderService {
    Order createOrder(ShoppingCart shoppingCart,
                      ShippingAddress shippingAddress,
                      BillingAddress billingAddress,
                      Payment payment,
                      String shippingMethod,
                      User user);

    Optional<Order> findById(Long id);
//    Order findOne(Long id);
}
