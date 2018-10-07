package com.webstore.service.impl;

import com.webstore.domain.Product;
import com.webstore.repository.ProductRepository;
import com.webstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {

        List<Product> productList = (List<Product>) productRepository.findAll();

        List<Product> activeProductList = new ArrayList<>();

        for (Product product : productList) {
            if(product.isActive()){
                activeProductList.add(product);
            }
        }

        return activeProductList;

//        return (List<Product>) productRepository.findAll();
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> findByCategory(String category){
        List<Product> productList = productRepository.findByCategory(category);

        List<Product> activeProductList = new ArrayList<>();

        for (Product product : productList) {
            if(product.isActive()){
                activeProductList.add(product);
            }
        }
        return activeProductList;
    }

    public List<Product> blurrySearch(String name) {
        List<Product> productList = productRepository.findByNameContaining(name);
        List<Product> activeProductList = new ArrayList<>();

        for (Product product: productList) {
            if(product.isActive()) {
                activeProductList.add(product);
            }
        }

        return activeProductList;
    }
}
