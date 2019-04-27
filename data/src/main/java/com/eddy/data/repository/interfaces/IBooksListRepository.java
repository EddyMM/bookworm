package com.eddy.data.repository.interfaces;

import com.eddy.data.models.Book;

import java.util.List;

public interface IBooksListRepository {

    List<Book> fetchBooks(String categoryCode);

}
