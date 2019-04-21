package com.eddy.data.models;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Book implements Serializable {

    String key;

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

    @SerializedName("rank")
    Integer rankThisWeek;

    @SerializedName("rank_last_week")
    Integer rankLastWeek;

    @SerializedName("weeks_on_list")
    Integer weeksOnList;


    public Book() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public String getPublisher() {
        return publisher;
    }

    public Integer getRankThisWeek() {
        return rankThisWeek;
    }

    public Integer getRankLastWeek() {
        return rankLastWeek;
    }

    public Integer getWeeksOnList() {
        return weeksOnList;
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
