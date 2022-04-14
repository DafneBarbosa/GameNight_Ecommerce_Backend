package com.dh.ctd.ecommerce_api.service;

import com.dh.ctd.ecommerce_api.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Category register(Category category);
    Optional<Category> findById(Integer id);
    void removeById(Integer id);
    List<Category> findAll();
    Category update(Category category);


}
