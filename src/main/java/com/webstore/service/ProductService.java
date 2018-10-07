package com.webstore.service;

import com.webstore.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    List<Product> findAll();

    Optional<Product> findById(Long id);

    List<Product> findByCategory(String category);

    List<Product> blurrySearch(String name);
}
