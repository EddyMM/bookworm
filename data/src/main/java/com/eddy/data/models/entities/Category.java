package com.eddy.data.models.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import timber.log.Timber;


public class Category implements Parcelable {

    @SerializedName("display_name")
    String displayName;

    @SerializedName("list_name")
    String listName;

    @SerializedName("list_name_encoded")
    String categoryCode;

    @SerializedName("newest_published_date")
    String newestPublishedDate;

    @SerializedName("updated")
    String updateFrequency;

    protected Category(Parcel in) {
        displayName = in.readString();
        listName = in.readString();
        categoryCode = in.readString();
        newestPublishedDate = in.readString();
        updateFrequency = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
        dest.writeString(listName);
        dest.writeString(categoryCode);
        dest.writeString(newestPublishedDate);
        dest.writeString(updateFrequency);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getNewestPublishedDate() {
        return newestPublishedDate;
    }

    public void setNewestPublishedDate(String newestPublishedDate) {
        this.newestPublishedDate = newestPublishedDate;
    }

    public void setUpdateFrequency(String updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public String getFormattedDate() {
        try {
            SimpleDateFormat sf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
            sf.applyLocalizedPattern("yyyy-MM-dd");
            Date date = sf.parse(newestPublishedDate);
            sf.applyLocalizedPattern("E, MMM dd, yyyy");
            return sf.format(date);
        } catch (ParseException e) {
            Timber.e(e);
            return null;
        }
    }

    public Category() {}


    public String getUpdateFrequency() {
        return updateFrequency;
    }

    @Override
    public String toString() {
        return "Category{" +
                ", displayName='" + displayName + '\'' +
                ", listName='" + listName + '\'' +
                ", categoryCode='" + categoryCode + '\'' +
                ", newestPublishedDate='" + newestPublishedDate + '\'' +
                ", updateFrequency='" + updateFrequency + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return (displayName.equals(category.displayName)) &&
                (listName.equals(category.listName)) &&
                (categoryCode.equals(category.categoryCode)) &&
                (newestPublishedDate.equals(category.newestPublishedDate)) &&
                (updateFrequency.equals(category.updateFrequency));
    }

    @Override
    public int hashCode() {
        return Objects.hash(displayName, listName, categoryCode, newestPublishedDate, updateFrequency);
    }
}