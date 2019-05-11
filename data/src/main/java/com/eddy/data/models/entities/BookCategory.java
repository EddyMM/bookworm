package com.eddy.data.models.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

@Entity(tableName = "book_category",
        primaryKeys = {"book_id", "category_code"},
        indices = {@Index(value = "category_code")},
        foreignKeys = {
            @ForeignKey(entity = Book.class, parentColumns = "id", childColumns = "book_id"),
            @ForeignKey(entity = Category.class, parentColumns = "category_code", childColumns = "category_code")
        })
public class BookCategory {

    @NonNull
    @ColumnInfo(name = "book_id")
    long bookId;

    @NonNull
    @ColumnInfo(name = "category_code")
    String categoryCode;

    public BookCategory(Integer bookId, String categoryCode) {
        this.bookId = bookId;
        this.categoryCode = categoryCode;
    }

    @NonNull
    public long getBookId() {
        return bookId;
    }

    @NonNull
    public String getCategoryCode() {
        return categoryCode;
    }

    public void setBookId(@NonNull long bookId) {
        this.bookId = bookId;
    }

    public void setCategoryCode(@NonNull String categoryCode) {
        this.categoryCode = categoryCode;
    }
}
