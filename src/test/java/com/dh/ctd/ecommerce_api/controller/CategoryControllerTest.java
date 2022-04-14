package com.dh.ctd.ecommerce_api.controller;

import com.dh.ctd.ecommerce_api.dto.CategoryDto;
import com.dh.ctd.ecommerce_api.dto.mapper.CategoryMapper;
import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.service.impl.CategoryServiceImpl;
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
public class CategoryControllerTest {

    Category postCategory1;
    Category mockCategory1;
    Category mockCategory2;
    CategoryDto mockCategoryDto1;
    CategoryDto mockCategoryDto2;
    List<Category> categoriesList = new ArrayList<Category>();
    List<CategoryDto> categoriesDtoList = new ArrayList<CategoryDto>();
    List<Category> emptyList = new ArrayList<>();

    @MockBean
    private CategoryServiceImpl service;
    @MockBean
    private CategoryMapper mapper;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void createCategories(){
        postCategory1 = new Category("Cooperativo","Todos contra o tabuleiro");
        mockCategory1 = new Category(1,"Cooperativo","Todos contra o tabuleiro");
        mockCategory2 = new Category(2,"Party Game","Festivo");
        mockCategoryDto1 = new CategoryDto(1,"Cooperativo","Todos contra o tabuleiro");
        mockCategoryDto2 = new CategoryDto(2,"Party Game","Festivo");
        categoriesList.add(mockCategory1);
        categoriesList.add(mockCategory2);
        categoriesDtoList.add(mockCategoryDto1);
        categoriesDtoList.add(mockCategoryDto2);
    }

    @Test
    @DisplayName("Should save Category")
    public void testSaveSucess() throws Exception {
        BDDMockito.given(mapper.toCategory(any(CategoryDto.class))).willReturn(postCategory1);
        BDDMockito.given(service.register(any(Category.class))).willReturn(mockCategory1);
        BDDMockito.given(mapper.toDto(any(Category.class))).willReturn(mockCategoryDto1);

        String json = new ObjectMapper().writeValueAsString(postCategory1);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                        .andExpect(status().isOk())
                        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                        .andExpect(jsonPath("$.id", is(1)))
                        .andExpect(jsonPath("$.name", is("Cooperativo")))
                        .andExpect(jsonPath("$.description", is("Todos contra o tabuleiro")));
    }

    @Test
    @DisplayName("Should return Category alreday reported")
    public void testSaveError() throws Exception {
        String json = new ObjectMapper().writeValueAsString(mockCategoryDto1);

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isAlreadyReported());
    }

    @Test
    @DisplayName("Should return Category with success")
    public void testFindBySuccess() throws Exception {
        doReturn(Optional.of(mockCategory1)).when(service).findById(1);
        doReturn(mockCategoryDto1).when(mapper).toDto(mockCategory1);

        mockMvc.perform(get("/categories/{id}",1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Cooperativo")))
                .andExpect(jsonPath("$.description", is("Todos contra o tabuleiro")));
    }

    @Test
    @DisplayName("Should return Category not found")
    public void testFindByIdNotFound() throws Exception {
        doReturn(Optional.empty()).when(service).findById(1);

        mockMvc.perform(get("/categories/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return all Categories")
    public void testFindAllSucess() throws Exception {
        doReturn(categoriesList).when(service).findAll();
        doReturn(mockCategoryDto1).when(mapper).toDto(mockCategory1);
        doReturn(mockCategoryDto2).when(mapper).toDto(mockCategory2);

        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(mockCategoryDto1.getName())))
                .andExpect(jsonPath("$[1].description", is(mockCategoryDto2.getDescription())));
    }

    @Test
    @DisplayName("Should Categories not found")
    public void testFindAllError() throws Exception {
        doReturn(emptyList).when(service).findAll();

        mockMvc.perform(get("/categories"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete a Category")
    public void testDeleteSucess() throws Exception {
        doReturn(Optional.of(mockCategory1)).when(service).findById(1);
        doNothing().when(service).removeById(1);

        mockMvc.perform(delete("/categories/{id}",1))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should return Category not found")
    public void testDeleteError() throws Exception {
        doReturn(Optional.empty()).when(service).findById(1);

        mockMvc.perform(delete("/categories/{id}",1))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should update a Category")
    public void testUpdateSucess() throws Exception {
        BDDMockito.given(service.findById(mockCategoryDto1.getId())).willReturn(Optional.of(mockCategory1));
        BDDMockito.given(mapper.toCategory(any(CategoryDto.class))).willReturn(mockCategory1);
        BDDMockito.given(service.update(any(Category.class))).willReturn(mockCategory1);
        BDDMockito.given(mapper.toDto(any(Category.class))).willReturn(mockCategoryDto1);

        String json = new ObjectMapper().writeValueAsString(mockCategoryDto1);

        mockMvc.perform(put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Cooperativo")))
                .andExpect(jsonPath("$.description", is("Todos contra o tabuleiro")));
    }

    @Test
    @DisplayName("Should Categories not found")
    public void testUpdateError() throws Exception {

        String json = new ObjectMapper().writeValueAsString(postCategory1);

        mockMvc.perform(put("/categories")
               .contentType(MediaType.APPLICATION_JSON)
               .accept(MediaType.APPLICATION_JSON)
               .content(json))
               .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should Categories not found")
    public void testUpdateError2() throws Exception {
        doReturn(Optional.empty()).when(service).findById(1);

        String json = new ObjectMapper().writeValueAsString(mockCategoryDto1);

        mockMvc.perform(put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return server error when all methods are called ")
    public void testServerError() throws Exception {
        when(mapper.toDto(any(Category.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(mapper.toCategory(any(CategoryDto.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.register(any(Category.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.findById(1)).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.findAll()).thenThrow(HttpServerErrorException.InternalServerError.class);
        when(service.update(any(Category.class))).thenThrow(HttpServerErrorException.InternalServerError.class);
        doThrow(HttpServerErrorException.InternalServerError.class).when(service).removeById(1);

        String json = new ObjectMapper().writeValueAsString(mockCategoryDto1);
        String json2 = new ObjectMapper().writeValueAsString(postCategory1);

        mockMvc.perform(put("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(get("/categories"))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(get("/categories/{id}",1))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json2))
                .andExpect(status().isInternalServerError());

        mockMvc.perform(delete("/categories/{id}",1))
                .andExpect(status().isInternalServerError());
    }

}
