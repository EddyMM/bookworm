package com.eddy.data;

import com.eddy.data.models.BookEntity;
import com.eddy.data.models.ListName;

import java.util.List;

public interface DataManager {

    List<ListName> getListNames();

    List<BookEntity> getBooks(String encodedListName);

}
