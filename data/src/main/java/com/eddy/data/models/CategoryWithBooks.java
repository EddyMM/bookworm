package com.eddy.data.models;

import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.Category;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class CategoryWithBooks {

    @Embedded
    public Category category;

    @Relation(parentColumn = "id", entityColumn = "category_code")
    public List<Book> books;

    public CategoryWithBooks(Category category, List<Book> books) {
        this.category = category;
        this.books = books;
    }

    public Category getCategory() {
        return category;
    }

    public List<Book> getBooks() {
        return books;
    }
}
