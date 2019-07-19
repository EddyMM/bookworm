package com.eddy.bookworm.books.list.viewmodel;

import android.app.Application;

import com.eddy.data.InjectorUtils;
import com.eddy.data.models.BookWithBuyLinks;
import com.eddy.data.models.entities.Category;
import com.eddy.data.repository.BooksListRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BooksListViewModel extends AndroidViewModel {

    private final BooksListRepository booksListRepository;
    private final Category category;

    private LiveData<List<BookWithBuyLinks>> booksLiveData;

    BooksListViewModel(@NonNull Application application, Category category) {
        super(application);

        this.category = category;

        booksListRepository = InjectorUtils.getBooksListRepository(getApplication());

        booksLiveData = booksListRepository.getBooksListLiveData(category);
    }

    public LiveData<List<BookWithBuyLinks>> getBooksLiveData() {
        return booksLiveData;
    }

    public LiveData<Boolean> getBooksSyncInProgress() {
        return booksListRepository.getSyncInProgress();
    }

    public void refreshBooks() {
        booksListRepository.syncBooks(category);
    }

    public LiveData<Throwable> getErrorLiveData() {
        return booksListRepository.getBooksListError();
    }
}
