package com.eddy.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListNamesResponse {

    @SerializedName("results")
    List<ListName> listNames;

    public List<ListName> getListNames() {
        return listNames;
    }

    @Override
    public String toString() {
        return "ListNamesResponse{" +
                "listNames=" + listNames +
                '}';
    }
}
