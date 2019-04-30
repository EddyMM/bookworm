package com.eddy.data.models.rest;

import com.eddy.data.models.entities.Book;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
}
