package com.dh.ctd.ecommerce_api.repository;

import com.dh.ctd.ecommerce_api.model.Category;
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
public class CategoryRepositoryTest {

    Category mockCategory1;
    Category mockCategory2;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    CategoryRepository repository;

    @BeforeEach
    public void createCategories(){
        mockCategory1 = new Category("Cooperativo","Todos contra o tabuleiro");
        mockCategory2 = new Category("Party Game","Festivo");
    }

    @Test
    @DisplayName("Should return all Categories")
    public void testFindAllSucess() {
        entityManager.persist(mockCategory1);
        entityManager.persist(mockCategory2);
        List<Category> categories = repository.findAll();
        assertThat(categories).hasSize(2).contains(mockCategory1,mockCategory2);
    }

    @Test
    @DisplayName("Should return Categories not found")
    public void testFindAllError() {
        List<Category> categories = repository.findAll();
        assertThat(categories).isEmpty();
    }

    @Test
    @DisplayName("Should save a Category")
    public void testSaveSucess() {
        Category category = repository.save(mockCategory1);
        assertThat(category).hasFieldOrPropertyWithValue("name","Cooperativo");
        assertThat(category).hasFieldOrPropertyWithValue("description","Todos contra o tabuleiro");
    }

    @Test
    @DisplayName("Should return Category with success")
    public void testFindBySuccess() {
        entityManager.persist(mockCategory1);
        Optional<Category> optionalCategory = repository.findById(mockCategory1.getId());
        Category category = optionalCategory.get();
        assertThat(optionalCategory.isPresent());
        assertThat(category).isEqualTo(mockCategory1);
    }

    @Test
    @DisplayName("Should return Category not found")
    public void testFindByError() {
        Optional<Category> category = repository.findById(1);
        assertThat(category.isEmpty());
    }

    @Test
    @DisplayName("Should delete a Category")
    public void testDeleteSucess() {
        entityManager.persist(mockCategory1);
        entityManager.persist(mockCategory2);
        repository.deleteById(mockCategory1.getId());
        List<Category> categories = repository.findAll();
        assertThat(categories).hasSize(1).contains(mockCategory2);
    }

}
