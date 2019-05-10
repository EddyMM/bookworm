package com.eddy.data.models.entities;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyLink buyLink = (BuyLink) o;
        return Objects.equals(name, buyLink.name) &&
                Objects.equals(url, buyLink.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, url);
    }
}
