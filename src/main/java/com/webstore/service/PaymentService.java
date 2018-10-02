package com.webstore.service;

import com.webstore.domain.Payment;
import com.webstore.domain.UserPayment;

public interface PaymentService {
    Payment setByUserPayment(UserPayment userPayment, Payment payment);
}
