package com.dh.ctd.ecommerce_api.dto.mapper;

import com.dh.ctd.ecommerce_api.dto.CategoryDto;
import com.dh.ctd.ecommerce_api.model.Category;
import org.springframework.stereotype.Component;

@Component

public class CategoryMapper {

    public CategoryDto toDto(Category category){
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }

    public Category toCategory(CategoryDto categoryDto){
        return new Category(
                categoryDto.getId(),
                categoryDto.getName(),
                categoryDto.getDescription()
        );
    }
}
