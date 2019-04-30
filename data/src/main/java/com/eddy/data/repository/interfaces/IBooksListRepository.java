package com.eddy.data.repository.interfaces;

import com.eddy.data.models.entities.Book;

import java.util.List;

public interface IBooksListRepository {

    List<Book> fetchBooks(String categoryCode, String apiKey);

}
