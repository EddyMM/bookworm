package com.eddy.data.repository;

import com.eddy.data.AppDataManager;
import com.eddy.data.models.Book;
import com.eddy.data.repository.interfaces.IBooksListRepository;

import java.util.List;

public class BooksListRepository implements IBooksListRepository {
    @Override
    public List<Book> fetchBooks(String categoryCode) {
        AppDataManager appDataManager = new AppDataManager();
        return appDataManager.getBooks(categoryCode);
    }
}
