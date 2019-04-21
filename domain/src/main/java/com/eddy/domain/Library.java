package com.eddy.domain;

import com.eddy.data.AppDataManager;
import com.eddy.data.models.Book;
import com.eddy.data.models.ListName;

import java.util.List;

public class Library {

    public static List<ListName> fetchCategories() {
        AppDataManager appDataManager = new AppDataManager();
        return appDataManager.getListNames();
    }

    public static List<Book> fetchBooks(String categoryCode) {
        AppDataManager appDataManager = new AppDataManager();
        return appDataManager.getBooks(categoryCode);
    }
}
