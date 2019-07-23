package com.eddy.data.dao;

import com.eddy.data.models.entities.Book;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

@Dao
public abstract class BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] addBooks(List<Book> books);

    @Update
    public abstract int updateBook(Book book);
}
