package com.dh.ctd.ecommerce_api.service;

import com.dh.ctd.ecommerce_api.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product register(Product product);
    Optional<Product> findById(Integer id);
    void removeById(Integer id);
    List<Product> findAll();
    Product update(Product product);
    List<Product> findByCategoryId(Integer category_id);
    List<Product> search(double min, double max);

}
