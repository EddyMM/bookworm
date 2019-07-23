package com.eddy.data.dao;

import com.eddy.data.models.entities.Book;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public abstract class BookDao {

    @Query("SELECT * FROM book")
    public abstract LiveData<List<Book>> getBooks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] addBooks(List<Book> books);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void addBook(Book book);

    @Update
    public abstract void updateBook(Book book);

    @Delete
    public abstract void deleteBook(Book book);

    @Query("SELECT * FROM book WHERE book.title=:title AND book.author=:author")
    public abstract LiveData<Book> getBook(String title, String author);
}
