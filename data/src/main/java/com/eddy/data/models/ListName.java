package com.eddy.data.models;

import com.google.gson.annotations.SerializedName;

public class ListName {

    @SerializedName("display_name")
    String displayName;

    @SerializedName("list_name")
    String listName;

    @SerializedName("list_name_encoded")
    String listNameEncoded;

    public String getDisplayName() {
        return displayName;
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
