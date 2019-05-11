package com.eddy.data.dao;

import com.eddy.data.models.entities.Book;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface BookDao {

    @Query("SELECT * FROM book where is_bookmarked=1")
    LiveData<List<Book>> getBookmarkedBooks();

    @Query("SELECT COUNT(*) FROM book INNER JOIN book_category " +
            "ON book.id=book_category.book_id " +
            "WHERE book_category.category_code=:categoryCode")
    boolean countBooksByCategoryCode(String categoryCode);
}
