package com.eddy.data;

import com.eddy.data.models.Book;
import com.eddy.data.models.ListName;

import java.util.List;

import androidx.lifecycle.LiveData;

public interface DataManager {

    LiveData<List<ListName>> getListNames();

    LiveData<List<Book>> getBooks(String encodedListName);

}
