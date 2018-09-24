package com.webstore.service;

import com.webstore.domain.UserShipping;

import java.util.Optional;

public interface UserShippingService {

    Optional<UserShipping> findById(Long id);

    void removeById(Long id);
}
