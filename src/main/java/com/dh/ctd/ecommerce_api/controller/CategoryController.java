package com.dh.ctd.ecommerce_api.controller;

import com.dh.ctd.ecommerce_api.dto.CategoryDto;
import com.dh.ctd.ecommerce_api.dto.mapper.CategoryMapper;
import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.service.impl.CategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping("/categories")
public class CategoryController {

    private CategoryServiceImpl categoryServiceImpl;
    private CategoryMapper mapper;

    @Autowired
    public CategoryController(CategoryServiceImpl categoryServiceImpl, CategoryMapper categoryMapper) {
        this.categoryServiceImpl = categoryServiceImpl;
        this.mapper = categoryMapper;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody CategoryDto categoryDto) {
        try {
            if(categoryDto.getId()!=null){
                return new ResponseEntity<>("Category was already created", HttpStatus.ALREADY_REPORTED);
            }
            Category category = mapper.toCategory(categoryDto);
            CategoryDto categoryDtoReturned = mapper.toDto(categoryServiceImpl.register(category));
            return new ResponseEntity<>(categoryDtoReturned, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            if(id==null){
                return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
            }
            if(categoryServiceImpl.findById(id).isPresent()){
                CategoryDto categoryDto = mapper.toDto(categoryServiceImpl.findById(id).get());
                return ResponseEntity.ok(categoryDto);
            }
            return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){
        try {
            List<CategoryDto> categoryDtoList = new ArrayList<>();
            for(Category category : categoryServiceImpl.findAll()){
                categoryDtoList.add(mapper.toDto(category));
            }
            if (categoryDtoList.isEmpty()){
                return new ResponseEntity<>("Categories not found",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(categoryDtoList);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Integer id){
        try {
            if(id==null){
                return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
            }
            if(categoryServiceImpl.findById(id).isPresent()){
                categoryServiceImpl.removeById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody CategoryDto categoryDto){
        try {
            if(categoryDto.getId()==null || categoryServiceImpl.findById(categoryDto.getId()).isEmpty()){
                return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
            }
            Category category = mapper.toCategory(categoryDto);
            CategoryDto categoryDtoReturned = mapper.toDto(categoryServiceImpl.update(category));
            return new ResponseEntity<>(categoryDtoReturned, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
