package com.dh.ctd.ecommerce_api.repository;

import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    Category mockCategory1;
    Category mockCategory2;
    Product mockProduct1;
    Product mockProduct2;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ProductRepository repository;

    @BeforeEach
    public void createCategories(){
        mockCategory1 = new Category("Cooperativo","Todos contra o tabuleiro");
        mockCategory2 = new Category("Party Game","Festivo");
        mockProduct1 = new Product("Mysterium",250.60,"Dixit+Detective","http://",10,7,3,"Português",60,mockCategory1);
        mockProduct2 = new Product("UNO",50.92,"uno","http://",5,10,2,"Português",20,mockCategory2);
    }

    @Test
    @DisplayName("Should return all Products")
    public void testFindAllSucess() {
        entityManager.persist(mockCategory1);
        entityManager.persist(mockCategory2);
        entityManager.persist(mockProduct1);
        entityManager.persist(mockProduct2);
        List<Product> products = repository.findAll();
        assertThat(products).hasSize(2).contains(mockProduct1,mockProduct2);
    }

    @Test
    @DisplayName("Should return Products not found")
    public void testFindAllError() {
        List<Product> products = repository.findAll();
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("Should save a Product")
    public void testSaveSucess() {
        entityManager.persist(mockCategory1);
        Product product = repository.save(mockProduct1);
        assertThat(product).hasFieldOrPropertyWithValue("title","Mysterium");
        assertThat(product).hasFieldOrPropertyWithValue("description","Dixit+Detective");
        assertThat(product).hasFieldOrPropertyWithValue("price",250.60);
        assertThat(product).hasFieldOrPropertyWithValue("image","http://");
        assertThat(product).hasFieldOrPropertyWithValue("minimumAge",10);
        assertThat(product).hasFieldOrPropertyWithValue("maximumPlayersNumber",7);
        assertThat(product).hasFieldOrPropertyWithValue("MinimumPlayersNumber",3);
        assertThat(product).hasFieldOrPropertyWithValue("language","Português");
        assertThat(product).hasFieldOrPropertyWithValue("playingTime",60);
        assertThat(product.getCategory()).hasFieldOrPropertyWithValue("id",mockCategory1.getId());
        assertThat(product.getCategory()).hasFieldOrPropertyWithValue("name","Cooperativo");
    }

    @Test
    @DisplayName("Should return Product with success")
    public void testFindBySuccess() {
        entityManager.persist(mockCategory1);
        entityManager.persist(mockProduct1);
        Optional<Product> optionalProduct = repository.findById(mockProduct1.getId());
        Product product = optionalProduct.get();
        assertThat(optionalProduct.isPresent());
        assertThat(product).isEqualTo(mockProduct1);
    }

    @Test
    @DisplayName("Should return Product not found")
    public void testFindByError() {
        Optional<Product> product = repository.findById(1);
        assertThat(product.isEmpty());
    }

    @Test
    @DisplayName("Should delete a Product")
    public void testDeleteSucess() {
        entityManager.persist(mockCategory1);
        entityManager.persist(mockCategory2);
        entityManager.persist(mockProduct1);
        entityManager.persist(mockProduct2);
        repository.deleteById(mockProduct1.getId());
        List<Product> products = repository.findAll();
        assertThat(products).hasSize(1).contains(mockProduct2);
    }

    @Test
    @DisplayName("Should return all Products by Category")
    public void testFindAllByCategorySucess() {
        entityManager.persist(mockCategory1);
        entityManager.persist(mockCategory2);
        entityManager.persist(mockProduct1);
        entityManager.persist(mockProduct2);
        List<Product> products = repository.findByCategoryId(mockCategory1.getId());
        assertThat(products).hasSize(1).contains(mockProduct1);
    }

    @Test
    @DisplayName("Should return Products not found")
    public void testFindAllByCategoryError() {
        List<Product> products = repository.findByCategoryId(20);
        assertThat(products).isEmpty();
    }

    @Test
    @DisplayName("Should return all Products by Price")
    public void testFindAllByPriceSucess() {
        entityManager.persist(mockCategory1);
        entityManager.persist(mockCategory2);
        entityManager.persist(mockProduct1);
        entityManager.persist(mockProduct2);
        List<Product> products = repository.search(0.0,70.0);
        assertThat(products).hasSize(1).contains(mockProduct2);
    }

    @Test
    @DisplayName("Should return Products not found")
    public void testFindAllByPriceError() {
        List<Product> products = repository.search(0.0,10.0);
        assertThat(products).isEmpty();
    }

}
