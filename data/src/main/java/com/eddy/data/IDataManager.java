package com.eddy.data;

import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.Category;

import java.util.List;

public interface IDataManager {

    List<Category> getListNames();

    List<Book> getBooks(String encodedListName);

}
