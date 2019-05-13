package com.eddy.bookworm.books.list.viewmodel;

import android.app.Application;

import com.eddy.data.InjectorUtils;
import com.eddy.data.models.BookWithBuyLinks;
import com.eddy.data.repository.BooksListRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BookmarksViewModel extends AndroidViewModel {

    public BookmarksViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<BookWithBuyLinks>> getBookmarkedBooksLiveData() {
        BooksListRepository booksListRepository = InjectorUtils.
                getBooksListRepository(getApplication());

        return booksListRepository.getBookmarkedBooks();
    }
}
