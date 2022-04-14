package com.dh.ctd.ecommerce_api.service;

import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.model.Product;
import com.dh.ctd.ecommerce_api.repository.ProductRepository;
import com.dh.ctd.ecommerce_api.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductServiceTest {

    Category mockCategory1;
    Category mockCategory2;
    Product mockProduct1;
    Product mockProduct2;

    @Autowired
    private ProductServiceImpl service;

    @MockBean
    private ProductRepository repository;

    @BeforeEach
    public void createProducts(){
        mockCategory1 = new Category(1,"Cooperativo","Todos contra o tabuleiro");
        mockCategory2 = new Category(2,"Party Game","Festivo");

        mockProduct1 = new Product(1,"Mysterium",250.60,"Dixit+Detective","http://",10,7,3,"Português",60,mockCategory1);
        mockProduct2 = new Product(2,"UNO",50.92,"uno","http://",5,10,2,"Português",20,mockCategory2);
    }

    @Test
    @DisplayName("Should save 2 Products")
    public void testSaveSucess() {
        // cenario
        Mockito.doReturn(mockProduct1).when(repository).save(mockProduct1);
        Mockito.doReturn(mockProduct2).when(repository).save(mockProduct2);

        // execucao
        Product product1 = service.register(mockProduct1);
        Product product2 = service.register(mockProduct2);

        // assert
        Assertions.assertSame(product1, mockProduct1,"Products should be the same");
        Assertions.assertSame(product2, mockProduct2,"Products should be the same");
    }

    @Test
    @DisplayName("Should save Products error")
    public void testSaveError() {
        // cenario
        Mockito.doReturn(mockProduct1).when(repository).save(mockProduct1);
        Mockito.doReturn(mockProduct2).when(repository).save(mockProduct2);

        // execucao
        Product product1 = service.register(mockProduct1);
        Product product2 = service.register(mockProduct2);

        // assert
        Assertions.assertNotSame(product1, mockProduct2,"Products should not be the same");
        Assertions.assertNotSame(product2, mockProduct1,"Products should not be the same");
    }

    @Test
    @DisplayName("Should return Product with success")
    public void testFindBySuccess() {
        // cenario
        Mockito.doReturn(Optional.of(mockProduct1)).when(repository).findById(1);

        // execucao
        Optional<Product> returnedProduct  = service.findById(1);

        // assert
        Assertions.assertTrue(returnedProduct.isPresent(), "Product was not found");
        Assertions.assertSame(returnedProduct.get(), mockProduct1, "Products must be the same");
    }

    @Test
    @DisplayName("Should return Product error")
    public void testFindByError() {
        // cenario
        Mockito.doReturn(Optional.of(mockProduct1)).when(repository).findById(1);

        // execucao
        Optional<Product> returnedProduct1  = service.findById(8);
        Optional<Product> returnedProduct2  = service.findById(1);

        // assert
        Assertions.assertFalse(returnedProduct1.isPresent(), "Product was not suposed to be found");
        Assertions.assertNotEquals(returnedProduct2.get(), mockProduct2, "Products should not be the same");
    }

    @Test
    @DisplayName("Should return all Products")
    public void testFindAllSucess() {
        //given
        List<Product> mockList = new ArrayList<Product>();
        mockList.add(mockProduct1);
        mockList.add(mockProduct2);
        Mockito.doReturn(mockList).when(repository).findAll();

        //when
        List<Product> products = service.findAll();
        int size = mockList.size();

        //then
        Assertions.assertEquals(products.size(),size,(products.size()<size)?"Not all products were found":"Too many products were found");
        Assertions.assertTrue(products.contains(mockProduct1));
        Assertions.assertTrue(products.contains(mockProduct2));
        assertThat(products).usingRecursiveComparison().isEqualTo(mockList);
    }

    @Test
    @DisplayName("Should return all Products error")
    public void testFindAllError() {
        //given
        List<Product> mockList1 = new ArrayList<Product>();
        mockList1.add(mockProduct1);
        Mockito.doReturn(mockList1).when(repository).findAll();

        List<Product> mockList2 = new ArrayList<Product>();
        mockList2.add(mockProduct2);

        //when
        List<Product> products = service.findAll();
        int size = mockList1.size();

        //then
        Assertions.assertNotEquals(products.size(), size-1, "There should be more products");
        Assertions.assertNotEquals(products.size(), size+1, "There should be less products");
        Assertions.assertFalse(products.contains(mockProduct2));
        assertThat(products).usingRecursiveComparison().isNotEqualTo(mockList2);
    }

    @Test
    @DisplayName("Should delete a Product")
    public void testDeleteSucess() {
        Mockito.doNothing().when(repository).deleteById(1);

        service.removeById(1);

        assertDoesNotThrow(() -> service.removeById(1));
    }

    @Test
    @DisplayName("Should update a Product")
    public void testUpdateSucess() {
        // cenario
        Mockito.doReturn(mockProduct1).when(repository).save(mockProduct1);
        Mockito.doReturn(mockProduct2).when(repository).save(mockProduct2);

        // execucao
        Product product1 = service.update(mockProduct1);
        Product product2 = service.update(mockProduct2);

        // assert
        Assertions.assertSame(product1, mockProduct1,"Products should be the same");
        Assertions.assertSame(product2, mockProduct2,"Products should be the same");
    }

    @Test
    @DisplayName("Should update Products error")
    public void testUpdateError() {
        // cenario
        Mockito.doReturn(mockProduct1).when(repository).save(mockProduct1);
        Mockito.doReturn(mockProduct2).when(repository).save(mockProduct2);

        // execucao
        Product product1 = service.update(mockProduct1);
        Product product2 = service.update(mockProduct2);

        // assert
        Assertions.assertNotSame(product1, mockProduct2,"Products should not be the same");
        Assertions.assertNotSame(product2, mockProduct1,"Products should not be the same");
    }

    @Test
    @DisplayName("Should return all Products with Category 1")
    public void testFindAllByCategorySucess() {
        List<Product> mockList = new ArrayList<Product>();
        mockList.add(mockProduct1);
        Mockito.doReturn(mockList).when(repository).findByCategoryId(1);

        //when
        List<Product> products = service.findByCategoryId(1);
        int size = mockList.size();

        //then
        Assertions.assertEquals(products.size(),size,(products.size()<size)?"Not all products were found":"Too many products were found");
        Assertions.assertTrue(products.contains(mockProduct1));
        assertThat(products).usingRecursiveComparison().isEqualTo(mockList);
    }

    @Test
    @DisplayName("Should return all Products error")
    public void testFindAllByCategoryError() {
        //given
        List<Product> mockList1 = new ArrayList<Product>();
        mockList1.add(mockProduct1);
        Mockito.doReturn(mockList1).when(repository).findByCategoryId(1);

        List<Product> mockList2 = new ArrayList<Product>();
        mockList2.add(mockProduct2);

        //when
        List<Product> products = service.findByCategoryId(1);
        int size = mockList1.size();

        //then
        Assertions.assertNotEquals(products.size(), size-1, "There should be more products");
        Assertions.assertNotEquals(products.size(), size+1, "There should be less products");
        Assertions.assertFalse(products.contains(mockProduct2));
        assertThat(products).usingRecursiveComparison().isNotEqualTo(mockList2);
    }

    @Test
    @DisplayName("Should return all Products up to $70 price")
    public void testFindAllByPriceSucess() {
        List<Product> mockList = new ArrayList<Product>();
        mockList.add(mockProduct2);
        Mockito.doReturn(mockList).when(repository).search(0.0,70.0);

        //when
        List<Product> products = service.search(0.0,70.0);
        int size = mockList.size();

        //then
        Assertions.assertEquals(products.size(),size,(products.size()<size)?"Not all products were found":"Too many products were found");
        Assertions.assertTrue(products.contains(mockProduct2));
        assertThat(products).usingRecursiveComparison().isEqualTo(mockList);
    }

    @Test
    @DisplayName("Should return all Products error")
    public void testFindAllByPriceError() {
        List<Product> mockList1 = new ArrayList<Product>();
        mockList1.add(mockProduct2);
        Mockito.doReturn(mockList1).when(repository).search(0.0,70.0);
        List<Product> mockList2 = new ArrayList<Product>();
        mockList2.add(mockProduct1);

        List<Product> products = service.search(0.0,70.0);
        int size = mockList1.size();

        Assertions.assertNotEquals(products.size(), size-1, "There should be more products");
        Assertions.assertNotEquals(products.size(), size+1, "There should be less products");
        Assertions.assertFalse(products.contains(mockProduct1));
        assertThat(products).usingRecursiveComparison().isNotEqualTo(mockList2);
    }




}
