package com.dh.ctd.ecommerce_api.controller;

import com.dh.ctd.ecommerce_api.dto.ProductDto;
import com.dh.ctd.ecommerce_api.dto.mapper.ProductMapper;
import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.model.Product;
import com.dh.ctd.ecommerce_api.service.impl.CategoryServiceImpl;
import com.dh.ctd.ecommerce_api.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    Category mockCategory1;
    Category mockCategory2;
    Product postProduct1;
    Product mockProduct1;
    Product mockProduct2;
    ProductDto postProductDto1;
    ProductDto postProductDto2;
    ProductDto mockProductDto1;
    ProductDto mockProductDto2;
    List<Product> productsList = new ArrayList<Product>();
    List<ProductDto> productsDtoList = new ArrayList<ProductDto>();
    List<Product> emptyList = new ArrayList<>();

    @MockBean
    private ProductServiceImpl service;
    @MockBean
    private CategoryServiceImpl categoryService;
    @MockBean
    private ProductMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void createProducts(){
        mockCategory1 = new Category(1,"Cooperativo","Todos contra o tabuleiro");
        mockCategory2 = new Category(2,"Party Game","Festivo");
        postProduct1 = new Product("Mysterium",250.60,"Dixit+Detective","http://",10,7,3,"Português",60,mockCategory1);
        mockProduct1 = new Product(1,"Mysterium",250.60,"Dixit+Detective","http://",10,7,3,"Português",60,mockCategory1);
        mockProduct2 = new Product(2,"UNO",50.92,"uno","http://",5,10,2,"Português",20,mockCategory1);
        mockProductDto1 = new ProductDto(1,"Mysterium",250.60,"Dixit+Detective","http://",10,7,3,"Português",60,1);
        mockProductDto2 = new ProductDto(2,"UNO",50.92,"uno","http://",5,10,2,"Português",20,1);
        postProductDto1 = new ProductDto("Mysterium",250.60,"Dixit+Detective","http://",10,7,3,"Português",60,1);
        postProductDto2 = new ProductDto("Mysterium",250.60,"Dixit+Detective","http://",10,7,3,"Português",60,null);
        productsList.add(mockProduct1);
        productsList.add(mockProduct2);
        productsDtoList.add(mockProductDto1);
        productsDtoList.add(mockProductDto2);
    }

    @Test
    @DisplayName("Should save Product")
    public void testSaveSucess() throws Exception {
        BDDMockito.given(categoryService.findById(1)).willReturn(Optional.of(mockCategory1));
        BDDMockito.given(mapper.toProduct(any(ProductDto.class),any(Category.class))).willReturn(mockProduct1);
        BDDMockito.given(service.register(any(Product.class))).willReturn(mockProduct1);
        BDDMockito.given(mapper.toDto(any(Product.class))).willReturn(mockProductDto1);

        String json = new ObjectMapper().writeValueAsString(postProductDto1);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(1)))
                        .andExpect(jsonPath("$.title", is("Mysterium")))
                        .andExpect(jsonPath("$.description", is("Dixit+Detective")));
    }

    @Test
    @DisplayName("Should return Product already reported")
    public void testSaveError() throws Exception {
        String json = new ObjectMapper().writeValueAsString(mockProductDto1);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAlreadyReported());
    }

    @Test
    @DisplayName("Should return Category not found")
    public void testSaveError2() throws Exception {
        String json = new ObjectMapper().writeValueAsString(postProductDto2);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return Category not found")
    public void testSaveError3() throws Exception {
        BDDMockito.given(categoryService.findById(1)).willReturn(Optional.empty());

        String json = new ObjectMapper().writeValueAsString(postProductDto1);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return Product with success")
    public void testFindBySuccess() throws Exception {
        doReturn(Optional.of(mockProduct1)).when(service).findById(1);
        doReturn(mockProductDto1).when(mapper).toDto(mockProduct1);

        mockMvc.perform(get("/products/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Mysterium")))
                .andExpect(jsonPath("$.description", is("Dixit+Detective")));
    }

    @Test
    @DisplayName("Should return product not found")
    public void testFindByIdNotFound() throws Exception {
        doReturn(Optional.empty()).when(service).findById(1);

        mockMvc.perform(get("/products/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return all Products")
    public void testFindAllSucess() throws Exception {
        doReturn(productsList).when(service).findAll();
        doReturn(mockProductDto1).when(mapper).toDto(mockProduct1);
        doReturn(mockProductDto2).when(mapper).toDto(mockProduct2);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(mockProductDto1.getTitle())))
                .andExpect(jsonPath("$[1].description", is(mockProductDto2.getDescription())));
    }

    @Test
    @DisplayName("Should Products not found")
    public void testFindAllError() throws Exception {
        doReturn(emptyList).when(service).findAll();

        mockMvc.perform(get("/products"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete a Product")
    public void testDeleteSucess() throws Exception {
        doReturn(Optional.of(mockProduct1)).when(service).findById(1);
        doNothing().when(service).removeById(1);

        mockMvc.perform(delete("/products/{id}",1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return Product not found")
    public void testDeleteError() throws Exception {
        doReturn(Optional.empty()).when(service).findById(1);

        mockMvc.perform(delete("/products/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update a Product")
    public void testUpdateSucess() throws Exception {
        BDDMockito.given(service.findById(1)).willReturn(Optional.of(mockProduct1));
        BDDMockito.given(categoryService.findById(1)).willReturn(Optional.of(mockCategory1));
        BDDMockito.given(mapper.toProduct(any(ProductDto.class),any(Category.class))).willReturn(mockProduct1);
        BDDMockito.given(service.update(any(Product.class))).willReturn(mockProduct1);
        BDDMockito.given(mapper.toDto(any(Product.class))).willReturn(mockProductDto1);

        String json = new ObjectMapper().writeValueAsString(mockProductDto1);

        mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Mysterium")))
                .andExpect(jsonPath("$.description", is("Dixit+Detective")));
    }

    @Test
    @DisplayName("Should Product not found")
    public void testUpdateError1() throws Exception {
        String json = new ObjectMapper().writeValueAsString(postProductDto1);

        mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Product not found")
    public void testUpdateError2() throws Exception {
        BDDMockito.given(service.findById(1)).willReturn(Optional.empty());

        String json = new ObjectMapper().writeValueAsString(mockProductDto1);

        mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Category not found")
    public void testUpdateError3() throws Exception {
        BDDMockito.given(service.findById(1)).willReturn(Optional.of(mockProduct1));

        String json = new ObjectMapper().writeValueAsString(postProductDto2);

        mockMvc.perform(put("/products")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Category not found")
    public void testUpdateError4() throws Exception {
        BDDMockito.given(service.findById(1)).willReturn(Optional.of(mockProduct1));
        BDDMockito.given(categoryService.findById(1)).willReturn(Optional.empty());

        String json = new ObjectMapper().writeValueAsString(mockProductDto1);

        mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return all Products with category 1")
    public void testFindAllByCategorySucess() throws Exception {
        BDDMockito.given(categoryService.findById(1)).willReturn(Optional.of(mockCategory1));
        BDDMockito.given(service.findByCategoryId(mockCategory1.getId())).willReturn(productsList);
        BDDMockito.given(mapper.toDto(mockProduct1)).willReturn(mockProductDto1);
        BDDMockito.given(mapper.toDto(mockProduct2)).willReturn(mockProductDto2);

        mockMvc.perform(get("/products/category/{categoryId}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(mockProductDto1.getTitle())))
                .andExpect(jsonPath("$[1].description", is(mockProductDto2.getDescription())));
    }

    @Test
    @DisplayName("Should Products not found")
    public void testFindAllByCategoryError() throws Exception {
        BDDMockito.given(categoryService.findById(1)).willReturn(Optional.empty());

        mockMvc.perform(get("/products/category/{categoryId}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Products not found")
    public void testFindAllByCategoryError2() throws Exception {
        doReturn(emptyList).when(service).findByCategoryId(1);

        mockMvc.perform(get("/products/category/{categoryId}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return all Products with price range")
    public void testFindAllByPriceSucess() throws Exception {
        BDDMockito.given(service.search(0.0,300.50)).willReturn(productsList);
        BDDMockito.given(mapper.toDto(mockProduct1)).willReturn(mockProductDto1);
        BDDMockito.given(mapper.toDto(mockProduct2)).willReturn(mockProductDto2);

        mockMvc.perform(get("/products/price/0.0/300.50"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is(mockProductDto1.getTitle())))
                .andExpect(jsonPath("$[1].description", is(mockProductDto2.getDescription())));
    }

    @Test
    @DisplayName("Should return Products not found")
    public void testFindAllByPriceError() throws Exception {
        doReturn(emptyList).when(service).search(0.0,300.50);

        mockMvc.perform(get("/products/price/{min}/{max}",0.0,300.50))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return server error when all methods are called ")
    public void testServerError() throws Exception {
        when(mapper.toDto(any(Product.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(mapper.toProduct(any(ProductDto.class),any(Category.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(categoryService.findById(any(Integer.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.register(any(Product.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.findById(any(Integer.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.findAll()).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.update(any(Product.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.findByCategoryId(any(Integer.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.search(any(double.class),any(double.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        doThrow(HttpServerErrorException.InternalServerError.class).when(service).removeById(1);

        String json = new ObjectMapper().writeValueAsString(mockProductDto1);
        String json2 = new ObjectMapper().writeValueAsString(postProductDto1);

        mockMvc.perform(put("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(get("/products"))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(get("/products/{id}",1))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json2))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(delete("/products/{id}",1))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(get("/products/category/{categoryId}",1))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(get("/products/price/{min}/{max}",0.0,300.50))
                .andExpect(status().isInternalServerError());
    }



}
