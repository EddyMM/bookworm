package com.eddy.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksResults {

    @SerializedName("bestsellers_date")
    String bestsellersDate;

    @SerializedName("books")
    List<BookEntity> books;

    public List<BookEntity> getBooks() {
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
