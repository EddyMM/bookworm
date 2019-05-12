package com.eddy.data.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.eddy.data.models.entities.Book;
import com.eddy.data.models.entities.BuyLink;

import java.util.List;

import androidx.room.Embedded;
import androidx.room.Relation;

public class BookWithBuyLinks implements Parcelable {

    @Embedded
    public Book book;

    @Relation(parentColumn = "id", entityColumn = "book_id")
    public List<BuyLink> buyLinks;

    public BookWithBuyLinks() {}

    protected BookWithBuyLinks(Parcel in) {
        book = in.readParcelable(Book.class.getClassLoader());
        buyLinks = in.createTypedArrayList(BuyLink.CREATOR);
    }

    public static final Creator<BookWithBuyLinks> CREATOR = new Creator<BookWithBuyLinks>() {
        @Override
        public BookWithBuyLinks createFromParcel(Parcel in) {
            return new BookWithBuyLinks(in);
        }

        @Override
        public BookWithBuyLinks[] newArray(int size) {
            return new BookWithBuyLinks[size];
        }
    };

    public Book getBook() {
        return book;
    }

    public List<BuyLink> getBuyLinks() {
        return buyLinks;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBuyLinks(List<BuyLink> buyLinks) {
        this.buyLinks = buyLinks;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(book, flags);
        dest.writeTypedList(buyLinks);
    }
}
