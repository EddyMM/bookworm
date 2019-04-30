package com.eddy.data.models.rest;

import com.google.gson.annotations.SerializedName;

public class BooksResponse {

    @SerializedName("results")
    BooksResults booksResults;

    @Override
    public String toString() {
        return "BooksResponse{" +
                "booksResults=" + booksResults +
                '}';
    }

    public BooksResults getBooksResults() {
        return booksResults;
    }
}
