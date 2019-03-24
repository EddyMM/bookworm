package com.eddy.bookworm.bookslist;

import com.eddy.data.AppDataManager;
import com.eddy.data.models.Book;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

class BooksListViewModel extends ViewModel {

    LiveData<List<Book>> booksLiveData;

    BooksListViewModel(String encodedListName) {

        AppDataManager appDataManager = new AppDataManager();
        booksLiveData = appDataManager.getBooks(encodedListName);
    }

}
