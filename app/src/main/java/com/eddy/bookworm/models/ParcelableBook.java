package com.eddy.bookworm.models;

import android.os.Parcel;
import android.os.Parcelable;


public class ParcelableBook implements Parcelable {

    String key;

    String title;

    String author;

    String description;

    String bookImageUrl;

    String publisher;

    Integer rankThisWeek;

    Integer rankLastWeek;

    Integer weeksOnList;

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

    public static Creator<ParcelableBook> getCREATOR() {
        return CREATOR;
    }

    public ParcelableBook() {}

    public ParcelableBook(Parcel in) {
        key = in.readString();
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
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
}
