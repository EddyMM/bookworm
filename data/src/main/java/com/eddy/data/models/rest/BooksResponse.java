package com.eddy.data.models.rest;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class BooksResponse {

    @SerializedName("results")
    BooksResults booksResults;

    public BooksResponse(BooksResults booksResults) {
        this.booksResults = booksResults;
    }

    @Override
    public String toString() {
        return "BooksResponse{" +
                "booksResults=" + booksResults +
                '}';
    }

    public BooksResults getBooksResults() {
        return booksResults;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BooksResponse that = (BooksResponse) o;
        return Objects.equals(booksResults, that.booksResults);
    }
}
