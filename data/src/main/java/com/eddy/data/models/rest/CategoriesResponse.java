package com.eddy.data.models.rest;

import com.eddy.data.models.entities.Category;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {

    @SerializedName("results")
    List<Category> categories;

    public List<Category> getCategories() {
        return categories;
    }

    public CategoriesResponse(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "CategoriesResponse{" +
                "categories=" + categories +
                '}';
    }
}
