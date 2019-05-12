package com.eddy.data.dao;

import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.BookCategory;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface BookCategoryDao {

    @Query("SELECT * FROM book INNER JOIN book_category " +
            "ON book.id=book_category.book_id " +
            "WHERE book_category.category_code=:categoryCode")
    LiveData<List<Book>> getBooksByCategoryCode(String categoryCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addBookCategory(BookCategory bookCategory);
}
