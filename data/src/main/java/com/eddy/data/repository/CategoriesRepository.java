package com.eddy.data.repository;

import com.eddy.data.DataManager;
import com.eddy.data.models.ListName;
import com.eddy.data.repository.interfaces.ICategoriesRepository;

import java.util.List;

public class CategoriesRepository implements ICategoriesRepository {
    @Override
    public List<ListName> fetchCategories() {
        DataManager dataManager = new DataManager();
        return dataManager.getListNames();
    }
}
