package com.dh.ctd.ecommerce_api.service.impl;

import com.dh.ctd.ecommerce_api.model.Product;
import com.dh.ctd.ecommerce_api.repository.ProductRepository;
import com.dh.ctd.ecommerce_api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    @Override
    public Product register(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> findById(Integer id) {
        return productRepository.findById(id);
    }

    @Override
    public void removeById(Integer id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product update(Product product){
        return this.register(product);
    }

    @Override
    public List<Product> findByCategoryId(Integer category_id) {
        return productRepository.findByCategoryId(category_id);
    }

    @Override
    public List<Product> search(double min, double max) {
        return productRepository.search(min,max);
    }


}
