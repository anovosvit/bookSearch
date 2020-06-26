package com.github.anovosvit.searchbook.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksResponse {
    @SerializedName("kind")
    @Expose
    private String kind;

    @SerializedName("items")
    @Expose
    List<Item> items = null;

    @SerializedName("totalItems")
    @Expose
    int totalItems;

    public String getKind() {
        return kind;
    }

    public List<Item> getItems() {
        return items;
    }

    public int getTotalItems() {
        return totalItems;
    }
}
