package com.eddy.data.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "buy_link",
        indices = {@Index(value = "book_id")},
        foreignKeys = {
            @ForeignKey(entity = Book.class,
                    parentColumns = "id",
                    childColumns = "book_id",
                    onDelete = ForeignKey.CASCADE)
        })
public class BuyLink implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    long id;

    @SerializedName("name")
    String name;

    @SerializedName("url")
    String url;

    @ColumnInfo(name = "book_id")
    long bookId;

    public BuyLink() {}

    protected BuyLink(Parcel in) {
        id = in.readLong();
        name = in.readString();
        url = in.readString();
        bookId = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeString(url);
        dest.writeLong(bookId);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "BuyLink{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", bookId='" + bookId + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyLink buyLink = (BuyLink) o;
        return Objects.equals(id, buyLink.id) &&
                Objects.equals(name, buyLink.name) &&
                Objects.equals(url, buyLink.url) &&
                Objects.equals(bookId, buyLink.bookId);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, url, bookId);
    }
}
