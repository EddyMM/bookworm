package com.eddy.data.repository.interfaces;

import com.eddy.data.models.entities.Category;

import java.util.List;

public interface ICategoriesRepository {

    List<Category> fetchCategories(String apiKey);

}
