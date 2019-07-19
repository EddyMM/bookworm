package com.eddy.bookworm.books.list.viewmodel;

import android.app.Application;
import android.content.Context;

import com.eddy.data.models.entities.Category;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class BooksListViewModelFactory implements ViewModelProvider.Factory {


    private Application application;
    private Category category;

    public BooksListViewModelFactory(Context context, Category category) {
        this.application = (Application) context.getApplicationContext();
        this.category = category;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BooksListViewModel(application, category);
    }
}
