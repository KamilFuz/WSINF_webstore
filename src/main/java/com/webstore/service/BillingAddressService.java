package com.webstore.service;

import com.webstore.domain.BillingAddress;
import com.webstore.domain.UserBilling;

public interface BillingAddressService {
    BillingAddress setByUserBilling(UserBilling userBilling, BillingAddress billingAddress);
}
