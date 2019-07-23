package com.eddy.data.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "buy_link",
        indices = {@Index(value = {"book_title", "book_author"})},
        foreignKeys = {
                @ForeignKey(entity = Book.class,
                        parentColumns = {"title", "author"},
                        childColumns = {"book_title", "book_author"},
                        onDelete = ForeignKey.CASCADE)
        })
public class BuyLink implements Parcelable {

    @SerializedName("name")
    String name;

    @PrimaryKey
    @SerializedName("url")
    @NonNull
    String url;

    @ColumnInfo(name = "book_title")
    String bookTitle;

    @ColumnInfo(name = "book_author")
    String bookAuthor;

    public BuyLink() {}


    protected BuyLink(Parcel in) {
        name = in.readString();
        url = in.readString();
        bookTitle = in.readString();
        bookAuthor = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public static final Creator<BuyLink> CREATOR = new Creator<BuyLink>() {
        @Override
        public BuyLink createFromParcel(Parcel in) {
            return new BuyLink(in);
        }

        @Override
        public BuyLink[] newArray(int size) {
            return new BuyLink[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(bookTitle);
        dest.writeString(bookAuthor);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyLink buyLink = (BuyLink) o;
        return Objects.equals(name, buyLink.name) &&
                Objects.equals(url, buyLink.url) &&
                Objects.equals(bookTitle, buyLink.bookTitle) &&
                Objects.equals(bookAuthor, buyLink.bookAuthor);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, url, bookTitle, bookAuthor);
    }

    @Override
    public String toString() {
        return "BuyLink{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                '}';
    }
}