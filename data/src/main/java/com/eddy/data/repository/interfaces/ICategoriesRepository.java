package com.eddy.data.repository.interfaces;

import com.eddy.data.models.ListName;

import java.util.List;

public interface ICategoriesRepository {

    List<ListName> fetchCategories();

}
