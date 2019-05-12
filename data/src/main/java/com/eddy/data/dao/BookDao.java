package com.eddy.data.dao;

import com.eddy.data.models.BookWithBuyLinks;
import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.BuyLink;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public abstract class BookDao {

    @Query("SELECT * FROM book where is_bookmarked=1")
    public abstract LiveData<List<Book>> getBookmarkedBooks();

    @Query("SELECT * FROM book INNER JOIN book_category " +
            "ON book.id=book_category.book_id " +
            "WHERE book_category.category_code=:categoryCode")
    public abstract LiveData<List<BookWithBuyLinks>> getBooksByCategoryCode(String categoryCode);

    @Query("SELECT COUNT(*) FROM book INNER JOIN book_category " +
            "ON book.id=book_category.book_id " +
            "WHERE book_category.category_code=:categoryCode")
    public abstract int countBooksByCategoryCode(String categoryCode);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] addBooks(List<Book> books);

    public void addBuyLinks(long bookId, List<BuyLink> buyLinks) {
        for (BuyLink buyLink: buyLinks) {
            buyLink.setBookId(bookId);
            addBuyLink(buyLink);
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract void addBuyLink(BuyLink buyLink);
}
