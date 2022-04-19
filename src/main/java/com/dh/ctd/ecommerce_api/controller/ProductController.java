package com.dh.ctd.ecommerce_api.controller;

import com.dh.ctd.ecommerce_api.dto.ProductDto;
import com.dh.ctd.ecommerce_api.dto.mapper.ProductMapper;
import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.model.Product;
import com.dh.ctd.ecommerce_api.service.impl.CategoryServiceImpl;
import com.dh.ctd.ecommerce_api.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://3.144.3.176:3000/", maxAge = 3600)
@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductServiceImpl productServiceImpl;
    private CategoryServiceImpl categoryServiceImpl;
    private ProductMapper mapper;

    @Autowired
    public ProductController(ProductServiceImpl productServiceImpl, CategoryServiceImpl categoryServiceImpl, ProductMapper productMapper){
        this.productServiceImpl = productServiceImpl;
        this.categoryServiceImpl = categoryServiceImpl;
        this.mapper = productMapper;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody ProductDto productDto) {
        try{
            if(productDto.getId()!=null){
                return new ResponseEntity<>("Product already created",HttpStatus.ALREADY_REPORTED);
            }
            if(productDto.getCategory_id()==null || categoryServiceImpl.findById(productDto.getCategory_id()).isEmpty()){
                return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
            }
            Category category = categoryServiceImpl.findById(productDto.getCategory_id()).get();
            Product product = mapper.toProduct(productDto,category);
            ProductDto productDtoReturned = mapper.toDto(productServiceImpl.register(product));
            return new ResponseEntity<>(productDtoReturned, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            if(productServiceImpl.findById(id).isPresent()){
                ProductDto productDto = mapper.toDto(productServiceImpl.findById(id).get());
                return ResponseEntity.ok(productDto);
            }
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll(){                                                         //Duvida: criar variavel ou chamar 2 vezes
        try {
            List<ProductDto> productDtoList = new ArrayList<>();
            for(Product product : productServiceImpl.findAll()){
                productDtoList.add(mapper.toDto(product));
            }
            if (productDtoList.isEmpty()){
                return new ResponseEntity<>("Products not found",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(productDtoList);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeById(@PathVariable Integer id){
        try {
            if(productServiceImpl.findById(id).isPresent()){
                productServiceImpl.removeById(id);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody ProductDto productDto){
        try {
            if(productDto.getId()==null || productServiceImpl.findById(productDto.getId()).isEmpty()){
                return new ResponseEntity<>("Product not found",HttpStatus.NOT_FOUND);
            }
            if(productDto.getCategory_id()==null|| categoryServiceImpl.findById(productDto.getCategory_id()).isEmpty() ){
                return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
            }
            Category category = categoryServiceImpl.findById(productDto.getCategory_id()).get();
            Product product = mapper.toProduct(productDto,category);
            ProductDto productDtoReturned = mapper.toDto(productServiceImpl.update(product));
            return new ResponseEntity<>(productDtoReturned, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{category_id}")
    public ResponseEntity<?> findByCategoryId(@PathVariable Integer category_id) {
        try {
            if (categoryServiceImpl.findById(category_id).isEmpty()) {
                return new ResponseEntity<>("Category not found",HttpStatus.NOT_FOUND);
            }
            List<ProductDto> productDtoList = new ArrayList<>();
            for(Product product : productServiceImpl.findByCategoryId(category_id)){
                productDtoList.add(mapper.toDto(product));
            }
            if (productDtoList.isEmpty()){
                return new ResponseEntity<>("Products not found",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(productDtoList);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/price/{min}/{max}")
    public ResponseEntity<?> search(@PathVariable("min") double min, @PathVariable("max") double max) {
        try {
            List<ProductDto> productDtoList = new ArrayList<>();
            for(Product product : productServiceImpl.search(min,max)){
                productDtoList.add(mapper.toDto(product));
            }
            if (productDtoList.isEmpty()){
                return new ResponseEntity<>("Products not found",HttpStatus.NOT_FOUND);
            }
            return ResponseEntity.ok(productDtoList);
        } catch (Exception e) {
            return new ResponseEntity<>("Sever Error",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}
