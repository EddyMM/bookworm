package com.eddy.data.models.rest;

import com.eddy.data.models.entities.Book;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

public class BooksResults {

    @SerializedName("bestsellers_date")
    String bestsellersDate;

    @SerializedName("books")
    List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public String getBestsellersDate() {
        return bestsellersDate;
    }

    @Override
    public String toString() {
        return "BooksResults{" +
                "bestsellersDate='" + bestsellersDate + '\'' +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooksResults that = (BooksResults) o;
        return Objects.equals(bestsellersDate, that.bestsellersDate) &&
                Objects.equals(books, that.books);
    }

    @Override
    public int hashCode() {

        return Objects.hash(bestsellersDate, books);
    }
}
