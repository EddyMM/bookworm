package com.eddy.bookworm.books.detail.viewmodel;

import android.app.Application;
import android.content.Context;

import com.eddy.data.models.entities.Book;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BookDetailViewModelFactory implements ViewModelProvider.Factory {

    private final Book book;
    private final Application application;

    public BookDetailViewModelFactory(Context context, Book book) {
        this.application = (Application) context.getApplicationContext();
        this.book = book;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BookDetailViewModel(application, book);
    }
}
