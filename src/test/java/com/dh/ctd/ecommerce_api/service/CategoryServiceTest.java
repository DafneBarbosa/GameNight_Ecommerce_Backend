package com.dh.ctd.ecommerce_api.service;

import com.dh.ctd.ecommerce_api.model.Category;
import com.dh.ctd.ecommerce_api.repository.CategoryRepository;
import com.dh.ctd.ecommerce_api.service.impl.CategoryServiceImpl;
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
public class CategoryServiceTest {

    Category mockCategory1;
    Category mockCategory2;

    @Autowired
    private CategoryServiceImpl service;

    @MockBean
    private CategoryRepository repository;

    @BeforeEach
    public void createCategories(){
        mockCategory1 = new Category(1,"Cooperativo","Todos contra o tabuleiro");
        mockCategory2 = new Category(2,"Party Game","Festivo");
    }

    @Test
    @DisplayName("Should save 2 Categories")
    public void testSaveSucess() {
        // cenario
        Mockito.doReturn(mockCategory1).when(repository).save(mockCategory1);
        Mockito.doReturn(mockCategory2).when(repository).save(mockCategory2);

        // execucao
        Category category1 = service.register(mockCategory1);
        Category category2 = service.register(mockCategory2);

        // assert
        Assertions.assertSame(category1, mockCategory1,"Categories should be the same");
        Assertions.assertSame(category2, mockCategory2,"Categories should be the same");
    }

    @Test
    @DisplayName("Should save Categories error")
    public void testSaveError() {
        // cenario
        Mockito.doReturn(mockCategory1).when(repository).save(mockCategory1);
        Mockito.doReturn(mockCategory2).when(repository).save(mockCategory2);

        // execucao
        Category category1 = service.register(mockCategory1);
        Category category2 = service.register(mockCategory2);

        // assert
        Assertions.assertNotSame(category1, mockCategory2,"Categories should not be the same");
        Assertions.assertNotSame(category2, mockCategory1,"Categories should not be the same");
    }

    @Test
    @DisplayName("Should return Category with success")
    public void testFindBySuccess() {
        // cenario
        Mockito.doReturn(Optional.of(mockCategory1)).when(repository).findById(1);

        // execucao
        Optional<Category> returnedCategory  = service.findById(1);

        // assert
        Assertions.assertTrue(returnedCategory.isPresent(), "Category was not found");
        Assertions.assertSame(returnedCategory.get(), mockCategory1, "Categories must be the same");
    }

    @Test
    @DisplayName("Should return Category error")
    public void testFindByError() {
        // cenario
        Mockito.doReturn(Optional.of(mockCategory1)).when(repository).findById(1);

        // execucao
        Optional<Category> returnedCategory1  = service.findById(8);
        Optional<Category> returnedCategory2  = service.findById(1);

        // assert
        Assertions.assertFalse(returnedCategory1.isPresent(), "Category was not suposed to be found");
        Assertions.assertNotEquals(returnedCategory2.get(), mockCategory2, "Categories should not be the same");
    }

    @Test
    @DisplayName("Should return all Categories")
    public void testFindAllSucess() {
        //given
        List<Category> mockList = new ArrayList<Category>();
        mockList.add(mockCategory1);
        mockList.add(mockCategory2);
        Mockito.doReturn(mockList).when(repository).findAll();

        //when
        List<Category> categories = service.findAll();
        int size = mockList.size();

        //then
        Assertions.assertEquals(categories.size(),size,(categories.size()<size)?"Not all categories were found":"Too many categories were found");
        Assertions.assertTrue(categories.contains(mockCategory1));
        Assertions.assertTrue(categories.contains(mockCategory2));
        assertThat(categories).usingRecursiveComparison().isEqualTo(mockList);
    }

    @Test
    @DisplayName("Should all Categories error")
    public void testFindAllError() {
        //given
        List<Category> mockList1 = new ArrayList<Category>();
        mockList1.add(mockCategory1);
        Mockito.doReturn(mockList1).when(repository).findAll();

        List<Category> mockList2 = new ArrayList<Category>();
        mockList2.add(mockCategory2);

        //when
        List<Category> categories = service.findAll();
        int size = mockList1.size();

        //then
        Assertions.assertNotEquals(categories.size(), size-1, "There should be more categories");
        Assertions.assertNotEquals(categories.size(), size+1, "There should be less categories");
        Assertions.assertFalse(categories.contains(mockCategory2));
        assertThat(categories).usingRecursiveComparison().isNotEqualTo(mockList2);
    }

    @Test
    @DisplayName("Should delete a Category")
    public void testDeleteSucess() {
        Mockito.doNothing().when(repository).deleteById(1);

        service.removeById(1);

        assertDoesNotThrow(() -> service.removeById(1));
    }

    @Test
    @DisplayName("Should update a Category")
    public void testUpdateSucess() {
        // cenario
        Mockito.doReturn(mockCategory1).when(repository).save(mockCategory1);
        Mockito.doReturn(mockCategory2).when(repository).save(mockCategory2);

        // execucao
        Category category1 = service.update(mockCategory1);
        Category category2 = service.update(mockCategory2);

        // assert
        Assertions.assertSame(category1, mockCategory1,"Categories should be the same");
        Assertions.assertSame(category2, mockCategory2,"Categories should be the same");
    }

    @Test
    @DisplayName("Should update Categories error")
    public void testUpdateError() {
        // cenario
        Mockito.doReturn(mockCategory1).when(repository).save(mockCategory1);
        Mockito.doReturn(mockCategory2).when(repository).save(mockCategory2);

        // execucao
        Category category1 = service.update(mockCategory1);
        Category category2 = service.update(mockCategory2);

        // assert
        Assertions.assertNotSame(category1, mockCategory2,"Categories should not be the same");
        Assertions.assertNotSame(category2, mockCategory1,"Categories should not be the same");
    }




}
