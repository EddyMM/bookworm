package com.eddy.data.models.entities;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Category {

    String key;

    @SerializedName("display_name")
    String displayName;

    @SerializedName("list_name")
    String listName;

    public String getListNameEncoded() {
        return listNameEncoded;
    }

    @SerializedName("list_name_encoded")
    String listNameEncoded;

    @SerializedName("newest_published_date")
    String newestPublishedDate;

    @SerializedName("updated")
    String updateFrequency;

    public String getDisplayName() {
        return displayName;
    }

    public String getFormattedDate() {
        try {
            SimpleDateFormat sf = (SimpleDateFormat) SimpleDateFormat.getDateInstance();
            sf.applyLocalizedPattern("yyyy-MM-dd");
            Date date = sf.parse(newestPublishedDate);
            sf.applyLocalizedPattern("E, MMM dd, yyyy");
            return sf.format(date);
        } catch (ParseException e) {
//            Timber.e(e);
            return null;
        }
    }

    public Category(String displayName, String listName, String listNameEncoded, String newestPublishedDate, String updateFrequency) {
        this.displayName = displayName;
        this.listName = listName;
        this.listNameEncoded = listNameEncoded;
        this.newestPublishedDate = newestPublishedDate;
        this.updateFrequency = updateFrequency;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUpdateFrequency() {
        return updateFrequency;
    }

    @Override
    public String toString() {
        return "Category{" +
                "displayName='" + displayName + '\'' +
                ", listName='" + listName + '\'' +
                ", listNameEncoded='" + listNameEncoded + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return (displayName.equals(category.displayName)) &&
                (listName.equals(category.listName)) &&
                (listNameEncoded.equals(category.listNameEncoded)) &&
                (newestPublishedDate.equals(category.newestPublishedDate)) &&
                (updateFrequency.equals(category.updateFrequency));
    }
}
