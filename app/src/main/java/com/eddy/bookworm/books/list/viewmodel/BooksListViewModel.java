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
import androidx.lifecycle.MutableLiveData;

public class BooksListViewModel extends AndroidViewModel {

    public BooksListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<BookWithBuyLinks>> getBooksLiveData(Category category) {

        LiveData<List<BookWithBuyLinks>> booksLiveData = new MutableLiveData<>();

        BooksListRepository booksListRepository = InjectorUtils.getBooksListRepository(getApplication());

        if (booksListRepository != null) {
            booksLiveData = booksListRepository.getBooksListLiveData(category);
        }

        return booksLiveData;
    }
}
