package com.webstore.service.impl;

import com.webstore.domain.UserShipping;
import com.webstore.repository.UserShippingRepository;
import com.webstore.service.UserShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserShippingServiceImpl implements UserShippingService {

    @Autowired
    private UserShippingRepository userShippingRepository;

    @Override
    public Optional<UserShipping> findById(Long id){
        return userShippingRepository.findById(id);
    }

    public void removeById(Long id) {
        userShippingRepository.deleteById(id);
    }
}
