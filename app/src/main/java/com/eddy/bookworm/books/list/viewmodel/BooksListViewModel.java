package com.eddy.bookworm.books.list.viewmodel;

import android.app.Application;

import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.Category;
import com.eddy.data.repository.BooksListRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BooksListViewModel extends AndroidViewModel {

    private final BooksListRepository booksListRepository;

    private LiveData<List<Book>> booksLiveData;

    BooksListViewModel(@NonNull Application application, Category category) {
        super(application);

        booksListRepository = new BooksListRepository(category);
        booksLiveData = booksListRepository.getBooksLiveData();
    }

    public LiveData<List<Book>> getBooksLiveData() {
        return booksLiveData;
    }

    public LiveData<Boolean> getBooksSyncInProgress() {
        return booksListRepository.getLoadingInProgressLiveData();
    }

    public void refreshBooks() {
        booksListRepository.refresh();
    }

    public LiveData<Throwable> getErrorLiveData() {
        return booksListRepository.getBooksErrorLiveData();
    }
}
