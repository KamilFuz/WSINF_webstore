package com.webstore.service.impl;

import com.webstore.domain.UserPayment;
import com.webstore.repository.UserPaymentRepository;
import com.webstore.service.UserPaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {

    @Autowired
    private UserPaymentRepository userPaymentRepository;

    @Override
    public Optional<UserPayment> findById(Long id) {
        return userPaymentRepository.findById(id);
    }

    public void removeById(Long id) {
        userPaymentRepository.deleteById(id);
    }
}
