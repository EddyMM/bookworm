package com.eddy.data.models;


import com.google.gson.annotations.SerializedName;


public class BookEntity {

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


    public BookEntity() {}

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBookImageUrl() {
        return bookImageUrl;
    }

    public void setBookImageUrl(String bookImageUrl) {
        this.bookImageUrl = bookImageUrl;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getRankThisWeek() {
        return rankThisWeek;
    }

    public void setRankThisWeek(Integer rankThisWeek) {
        this.rankThisWeek = rankThisWeek;
    }

    public Integer getRankLastWeek() {
        return rankLastWeek;
    }

    public void setRankLastWeek(Integer rankLastWeek) {
        this.rankLastWeek = rankLastWeek;
    }

    public Integer getWeeksOnList() {
        return weeksOnList;
    }

    public void setWeeksOnList(Integer weeksOnList) {
        this.weeksOnList = weeksOnList;
    }

    @Override
    public String toString() {
        return "BookEntity{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", bookImageUrl='" + bookImageUrl + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}