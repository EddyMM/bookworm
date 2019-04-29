package com.eddy.bookworm.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class ParcelableBook implements Parcelable {

    String key;

    String title;

    String author;

    String description;

    String bookImageUrl;

    Integer bookImageWidth;

    Integer bookImageHeight;

    String publisher;

    Integer rankThisWeek;

    Integer rankLastWeek;

    Integer weeksOnList;

    List<ParcelableBuyLink> buyingLinks;

    String reviewsUrl;

    public ParcelableBook(Parcel in) {
        key = in.readString();
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
        buyingLinks = in.createTypedArrayList(ParcelableBuyLink.CREATOR);
        reviewsUrl = in.readString();
    }

    public ParcelableBook() { }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
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
        dest.writeTypedList(buyingLinks);
        dest.writeString(reviewsUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ParcelableBook> CREATOR = new Creator<ParcelableBook>() {
        @Override
        public ParcelableBook createFromParcel(Parcel in) {
            return new ParcelableBook(in);
        }

        @Override
        public ParcelableBook[] newArray(int size) {
            return new ParcelableBook[size];
        }
    };

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

    public List<ParcelableBuyLink> getBuyingLinks() {
        return buyingLinks;
    }

    public void setBuyingLinks(List<ParcelableBuyLink> buyingLinks) {
        this.buyingLinks = buyingLinks;
    }

    public String getReviewsUrl() {
        return reviewsUrl;
    }

    public void setReviewsUrl(String reviewsUrl) {
        this.reviewsUrl = reviewsUrl;
    }
}
