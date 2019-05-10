package com.eddy.data;


import com.eddy.data.models.entities.Category;
import com.eddy.data.repository.CategoriesRepository;

import org.junit.Test;

import java.util.List;

public class TestCategoriesRepository {

    @Test
    public void testFetchCategories() {
        MockBooksApiService mockBooksApiService = new MockBooksApiService();
        CategoriesRepository categoriesRepository = new CategoriesRepository(
                mockBooksApiService);

        List<Category> categories = categoriesRepository.fetchCategories();

        assert mockBooksApiService.generateTestCategories().equals(categories);
    }
}