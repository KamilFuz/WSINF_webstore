package com.webstore.service;


import com.webstore.domain.UserPayment;

import java.util.Optional;

public interface UserPaymentService {

    Optional<UserPayment> findById(Long id);
}
