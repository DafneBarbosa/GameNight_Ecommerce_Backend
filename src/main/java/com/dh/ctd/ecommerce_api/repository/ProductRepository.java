package com.dh.ctd.ecommerce_api.repository;

import com.dh.ctd.ecommerce_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByCategoryId(Integer category_id);
    @Query("from Product where price between :min and :max")
    List<Product> search(@Param("min") double min, @Param("max") double max);
}
