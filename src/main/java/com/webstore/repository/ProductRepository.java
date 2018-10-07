package com.webstore.repository;

import com.webstore.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository <Product, Long>{
    List<Product> findByCategory(String category);
    List<Product> findByNameContaining(String title);
}
