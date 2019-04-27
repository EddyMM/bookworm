package com.eddy.data;

import com.eddy.data.models.Book;
import com.eddy.data.models.ListName;

import java.util.List;

public interface DataManager {

    List<ListName> getListNames();

    List<Book> getBooks(String encodedListName);

}
