package com.webstore.service;

import com.webstore.domain.ShippingAddress;
import com.webstore.domain.UserShipping;

public interface ShippingAddressService {
    ShippingAddress setByUserShipping(UserShipping userShipping, ShippingAddress shippingAddress);
}
