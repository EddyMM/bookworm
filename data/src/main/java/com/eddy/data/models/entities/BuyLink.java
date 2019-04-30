package com.eddy.data.models.entities;

import com.google.gson.annotations.SerializedName;

public class BuyLink {

    @SerializedName("name")
    String name;

    @SerializedName("url")
    String url;

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
}
