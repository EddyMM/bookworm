package com.eddy.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Book implements Parcelable {

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


    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        description = in.readString();
        bookImageUrl = in.readString();
        publisher = in.readString();
        if (in.readByte() == 0) {
            rankThisWeek = null;
        } else {
            rankThisWeek = in.readInt();
        }
        if (in.readByte() == 0) {
            rankLastWeek = null;
        } else {
            rankLastWeek = in.readInt();
        }
        if (in.readByte() == 0) {
            weeksOnList = null;
        } else {
            weeksOnList = in.readInt();
        }
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(description);
        dest.writeString(bookImageUrl);
        dest.writeString(publisher);
        if (rankThisWeek == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rankThisWeek);
        }
        if (rankLastWeek == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(rankLastWeek);
        }
        if (weeksOnList == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(weeksOnList);
        }
    }
}
