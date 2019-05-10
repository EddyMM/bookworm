package com.eddy.data;

import com.eddy.data.models.entities.Book;
import com.eddy.data.rest.BooksApiService;

import java.util.List;

public interface IDataManager {

    List<Book> getBooks(BooksApiService booksApiService, String encodedListName);
}
