package com.eddy.data.models.entities;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "book", primaryKeys = {"title", "author"})
public class Book implements Parcelable {

    @SerializedName("title")
    @NonNull
    String title;

    @SerializedName("author")
    @NonNull
    String author;

    @SerializedName("description")
    String description;

    @ColumnInfo(name = "book_image_url")
    @SerializedName("book_image")
    String bookImageUrl;

    @ColumnInfo(name = "book_image_width")
    @SerializedName("book_image_width")
    Integer bookImageWidth;

    @ColumnInfo(name = "book_image_height")
    @SerializedName("book_image_height")
    Integer bookImageHeight;

    @ColumnInfo(name = "buy_links")
    @SerializedName("buy_links")
    @Ignore
    List<BuyLink> buyLinks;

    @ColumnInfo(name = "review_url")
    @SerializedName("book_review_link")
    String reviewUrl;

    @SerializedName("publisher")
    String publisher;

    @ColumnInfo(name = "rank_this_week")
    @SerializedName("rank")
    Integer rankThisWeek;

    @ColumnInfo(name = "rank_last_week")
    @SerializedName("rank_last_week")
    Integer rankLastWeek;

    @ColumnInfo(name = "weeks_on_list")
    @SerializedName("weeks_on_list")
    Integer weeksOnList;

    public Book() {}

    protected Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        description = in.readString();
        bookImageUrl = in.readString();
        if (in.readByte() == 0) {
            bookImageWidth = null;
        } else {
            bookImageWidth = in.readInt();
        }
        if (in.readByte() == 0) {
            bookImageHeight = null;
        } else {
            bookImageHeight = in.readInt();
        }
        buyLinks = in.createTypedArrayList(BuyLink.CREATOR);
        reviewUrl = in.readString();
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

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    public void setAuthor(@NonNull String author) {
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

    public Integer getBookImageWidth() {
        return bookImageWidth;
    }

    public void setBookImageWidth(Integer bookImageWidth) {
        this.bookImageWidth = bookImageWidth;
    }

    public Integer getBookImageHeight() {
        return bookImageHeight;
    }

    public void setBookImageHeight(Integer bookImageHeight) {
        this.bookImageHeight = bookImageHeight;
    }

    public List<BuyLink> getBuyLinks() {
        return buyLinks;
    }

    public void setBuyLinks(List<BuyLink> buyLinks) {
        this.buyLinks = buyLinks;
    }

    public String getReviewUrl() {
        return reviewUrl;
    }

    public void setReviewUrl(String reviewUrl) {
        this.reviewUrl = reviewUrl;
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(description);
        dest.writeString(bookImageUrl);
        if (bookImageWidth == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bookImageWidth);
        }
        if (bookImageHeight == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(bookImageHeight);
        }
        dest.writeTypedList(buyLinks);
        dest.writeString(reviewUrl);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(description, book.description) &&
                Objects.equals(bookImageUrl, book.bookImageUrl) &&
                Objects.equals(bookImageWidth, book.bookImageWidth) &&
                Objects.equals(bookImageHeight, book.bookImageHeight) &&
                Objects.equals(buyLinks, book.buyLinks) &&
                Objects.equals(reviewUrl, book.reviewUrl) &&
                Objects.equals(publisher, book.publisher) &&
                Objects.equals(rankThisWeek, book.rankThisWeek) &&
                Objects.equals(rankLastWeek, book.rankLastWeek) &&
                Objects.equals(weeksOnList, book.weeksOnList);
    }

    @Override
    public int hashCode() {

        return Objects.hash(title, author, description, bookImageUrl, bookImageWidth, bookImageHeight, buyLinks, reviewUrl, publisher, rankThisWeek, rankLastWeek, weeksOnList);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", description='" + description + '\'' +
                ", bookImageUrl='" + bookImageUrl + '\'' +
                ", bookImageWidth=" + bookImageWidth +
                ", bookImageHeight=" + bookImageHeight +
                ", buyLinks=" + buyLinks +
                ", reviewUrl='" + reviewUrl + '\'' +
                ", publisher='" + publisher + '\'' +
                ", rankThisWeek=" + rankThisWeek +
                ", rankLastWeek=" + rankLastWeek +
                ", weeksOnList=" + weeksOnList +
                '}';
    }
}
