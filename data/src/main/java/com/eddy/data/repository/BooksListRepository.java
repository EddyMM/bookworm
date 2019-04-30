package com.eddy.data.repository;

import com.eddy.data.DataManager;
import com.eddy.data.models.entities.Book;
import com.eddy.data.repository.interfaces.IBooksListRepository;

import java.util.List;

public class BooksListRepository implements IBooksListRepository {
    @Override
    public List<Book> fetchBooks(String categoryCode, String apiKey) {
        DataManager dataManager = new DataManager();
        return dataManager.getBooks(categoryCode, apiKey);
    }
}
