package com.dh.ctd.ecommerce_api.dto.mapper;

import com.dh.ctd.ecommerce_api.dto.ProductDto;
import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDto toDto(Product product){
        return new ProductDto(
                product.getId(),
                product.getTitle(),
                product.getPrice(),
                product.getDescription(),
                product.getImage(),
                product.getMinimumAge(),
                product.getMaximumPlayersNumber(),
                product.getMinimumPlayersNumber(),
                product.getLanguage(),
                product.getPlayingTime(),
                product.getCategory().getId()
        );
    }

    public Product toProduct(ProductDto productDto, Category category){
        return new Product(
                productDto.getId(),
                productDto.getTitle(),
                productDto.getPrice(),
                productDto.getDescription(),
                productDto.getImage(),
                productDto.getMinimumAge(),
                productDto.getMaximumPlayersNumber(),
                productDto.getMinimumPlayersNumber(),
                productDto.getLanguage(),
                productDto.getPlayingTime(),
                category
        );
    }

}
