package com.eddy.data.models;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import timber.log.Timber;

public class ListName {

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
            Timber.e(e);
            return null;
        }
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
        return "ListName{" +
                "displayName='" + displayName + '\'' +
                ", listName='" + listName + '\'' +
                ", listNameEncoded='" + listNameEncoded + '\'' +
                '}';
    }
}
