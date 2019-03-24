package com.eddy.data.models;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("title")
    String title;

    @SerializedName("author")
    String author;

    @SerializedName("description")
    String description;

    @SerializedName("book_image")
    String bookImageUrl;

    @SerializedName("publisher")
    String publisher;

    public String getTitle() {
        return title;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", bookImageUrl='" + bookImageUrl + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
