package com.dh.ctd.ecommerce_api.mapper;

import com.dh.ctd.ecommerce_api.dto.CategoryDto;
import com.dh.ctd.ecommerce_api.dto.mapper.CategoryMapper;
import com.dh.ctd.ecommerce_api.model.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CategoryMapperTest {

    Category mockCategory1;
    CategoryDto mockCategoryDto1;
    CategoryDto mockCategoryDto2;

    @Autowired
    private CategoryMapper mapper;

    @BeforeEach
    public void createCategories() {
        mockCategory1 = new Category(1, "Cooperativo", "Todos contra o tabuleiro");
        mockCategoryDto1 = new CategoryDto(1, "Cooperativo", "Todos contra o tabuleiro");
        mockCategoryDto2 = new CategoryDto(2, "Party Game", "Festivo");
    }

    @Test
    @DisplayName("Should map a Category")
    public void testMapSucess() {
        CategoryDto categoryDto1 = mapper.toDto(mockCategory1);

        Assertions.assertTrue(categoryDto1.getId()==mockCategoryDto1.getId(), "Categories should be the same");
        Assertions.assertTrue(categoryDto1.getName()==mockCategoryDto1.getName(), "Categories should be the same");
        Assertions.assertTrue(categoryDto1.getDescription()==mockCategoryDto1.getDescription(), "Categories should be the same");
    }

    @Test
    @DisplayName("Should map a Category error")
    public void testMapError() {
        CategoryDto categoryDto1 = mapper.toDto(mockCategory1);

        Assertions.assertFalse(categoryDto1.getId()==mockCategoryDto2.getId(), "Categories should not the same");
    }

    @Test
    @DisplayName("Should map a CategoryDto")
    public void testMapDtoSucess() {
        Category category = mapper.toCategory(mockCategoryDto1);

        Assertions.assertTrue(category.getId()==mockCategory1.getId(), "Categories should be the same");
        Assertions.assertTrue(category.getName()==mockCategory1.getName(), "Categories should be the same");
        Assertions.assertTrue(category.getDescription()==mockCategory1.getDescription(), "Categories should be the same");
    }

    @Test
    @DisplayName("Should map a CategoryDto error")
    public void testMapDtoError() {
        Category category = mapper.toCategory(mockCategoryDto1);

        Assertions.assertFalse(category.getId()==mockCategoryDto2.getId(), "Categories should not the same");
    }

}