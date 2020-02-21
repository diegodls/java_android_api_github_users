package com.example.android_java_api_github.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by DiegoL on 2/20/20
 */
public class ItemResponse {
    @SerializedName("items")
    @Expose
    private List<Item> items;

    public List<Item> getItems(){
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
