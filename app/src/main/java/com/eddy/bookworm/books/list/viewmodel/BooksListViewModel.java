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

    public LiveData<List<BookWithBuyLinks>> getBooksLiveData() {
        return booksLiveData;
    }

    private LiveData<List<BookWithBuyLinks>> booksLiveData;

    public BooksListViewModel(@NonNull Application application) {
        super(application);
    }

    private void fetchBooksList(Category category, boolean forceFetchOnline) {
        BooksListRepository booksListRepository = InjectorUtils.getBooksListRepository(getApplication());

        if (booksListRepository != null) {
            booksLiveData = booksListRepository.getBooksListLiveData(category, forceFetchOnline);
        }
    }

    public LiveData<Boolean> syncNeeded(Category category) {
        BooksListRepository booksListRepository = InjectorUtils.getBooksListRepository(getApplication());
        return booksListRepository.syncNeeded(category);
    }

    public LiveData<Boolean> getBooksSyncInProgress(Category category, boolean forceFetchOnline) {
        BooksListRepository booksListRepository = InjectorUtils.getBooksListRepository(getApplication());
        fetchBooksList(category, forceFetchOnline);

        return booksListRepository.getSyncInProgress();
    }
}
