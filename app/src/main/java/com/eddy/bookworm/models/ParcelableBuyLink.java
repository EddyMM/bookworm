package com.eddy.bookworm.models;

import android.os.Parcel;
import android.os.Parcelable;

public class ParcelableBuyLink implements Parcelable {

    private String name;

    private String url;

    public ParcelableBuyLink(Parcel in) {
        name = in.readString();
        url = in.readString();
    }

    public static final Creator<ParcelableBuyLink> CREATOR = new Creator<ParcelableBuyLink>() {
        @Override
        public ParcelableBuyLink createFromParcel(Parcel in) {
            return new ParcelableBuyLink(in);
        }

        @Override
        public ParcelableBuyLink[] newArray(int size) {
            return new ParcelableBuyLink[size];
        }
    };

    public ParcelableBuyLink() {

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

    @Override
    public String toString() {
        return "BuyLink{" +
                "name='" + name + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
    }
}
