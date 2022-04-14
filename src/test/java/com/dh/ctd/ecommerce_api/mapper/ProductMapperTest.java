package com.dh.ctd.ecommerce_api.mapper;

import com.dh.ctd.ecommerce_api.dto.ProductDto;
import com.dh.ctd.ecommerce_api.dto.mapper.ProductMapper;
import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.model.Product;
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
public class ProductMapperTest {

    Category mockCategory1;
    Product mockProduct1;
    ProductDto mockProductDto1;
    ProductDto mockProductDto2;

    @Autowired
    private ProductMapper mapper;

    @BeforeEach
    public void createProducts() {
        mockCategory1 = new Category(1, "Cooperativo", "Todos contra o tabuleiro");
        mockProduct1 = new Product(1,"Mysterium",250.6,"Dixit+Detective","http://",10,7,3,"Português",60,mockCategory1);
        mockProductDto1 = new ProductDto(1,"Mysterium",250.6,"Dixit+Detective","http://",10,7,3,"Português",60,1);
        mockProductDto2 = new ProductDto(2,"UNO",50.92,"uno","http://",5,10,2,"Português",20,2);
    }

    @Test
    @DisplayName("Should map a Product")
    public void testMapSucess() {
        ProductDto productDto1 = mapper.toDto(mockProduct1);

        Assertions.assertTrue(productDto1.getId()==mockProductDto1.getId(), "Products should be the same");
        Assertions.assertTrue(productDto1.getTitle()==mockProductDto1.getTitle(), "Products should be the same");
        Assertions.assertTrue(productDto1.getDescription()==mockProductDto1.getDescription(), "Products should be the same");
        Assertions.assertTrue(productDto1.getImage()==mockProductDto1.getImage(), "Products should be the same");
        Assertions.assertTrue(productDto1.getCategory_id()==mockProductDto1.getCategory_id(), "Products should be the same");
        Assertions.assertTrue(productDto1.getLanguage()==mockProductDto1.getLanguage(), "Products should be the same");
        Assertions.assertTrue(productDto1.getMaximumPlayersNumber()==mockProductDto1.getMaximumPlayersNumber(), "Products should be the same");
        Assertions.assertTrue(productDto1.getMinimumAge()==mockProductDto1.getMinimumAge(), "Products should be the same");
        Assertions.assertTrue(productDto1.getMinimumPlayersNumber()==mockProductDto1.getMinimumPlayersNumber(), "Products should be the same");
        Assertions.assertTrue(productDto1.getPlayingTime()==mockProductDto1.getPlayingTime(), "Products should be the same");
    }

    @Test
    @DisplayName("Should map a Product error")
    public void testMapError() {
        ProductDto productDto1 = mapper.toDto(mockProduct1);

        Assertions.assertFalse(productDto1.getId()==mockProductDto2.getId(), "Products should not the same");
    }

    @Test
    @DisplayName("Should map a ProductDto")
    public void testMapDtoSucess() {
        Product product = mapper.toProduct(mockProductDto1,mockCategory1);

        Assertions.assertTrue(product.getId()==mockProduct1.getId(), "Products should be the same");
        Assertions.assertTrue(product.getTitle()==mockProduct1.getTitle(), "Products should be the same");
        Assertions.assertTrue(product.getDescription()==mockProduct1.getDescription(), "Products should be the same");
        Assertions.assertTrue(product.getImage()==mockProduct1.getImage(), "Products should be the same");
        Assertions.assertTrue(product.getCategory().getId()==mockProduct1.getCategory().getId(), "Products should be the same");
        Assertions.assertTrue(product.getCategory().getName()==mockProduct1.getCategory().getName(), "Products should be the same");
        Assertions.assertTrue(product.getCategory().getDescription()==mockProduct1.getCategory().getDescription(), "Products should be the same");
        Assertions.assertTrue(product.getLanguage()==mockProduct1.getLanguage(), "Products should be the same");
        Assertions.assertTrue(product.getMaximumPlayersNumber()==mockProduct1.getMaximumPlayersNumber(), "Products should be the same");
        Assertions.assertTrue(product.getMinimumAge()==mockProduct1.getMinimumAge(), "Products should be the same");
        Assertions.assertTrue(product.getMinimumPlayersNumber()==mockProduct1.getMinimumPlayersNumber(), "Products should be the same");
        Assertions.assertTrue(product.getPlayingTime()==mockProduct1.getPlayingTime(), "Products should be the same");
    }

    @Test
    @DisplayName("Should map a ProductDto error")
    public void testMapDtoError() {
        Product product = mapper.toProduct(mockProductDto1,mockCategory1);

        Assertions.assertFalse(product.getId()==mockProductDto2.getId(), "Products should not the same");
    }

}