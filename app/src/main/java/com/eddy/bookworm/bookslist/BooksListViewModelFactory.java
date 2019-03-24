package com.eddy.bookworm.bookslist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class BooksListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private String encodedListName;

    BooksListViewModelFactory(String encodedListName) {
        this.encodedListName = encodedListName;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BooksListViewModel(encodedListName);
    }
}
