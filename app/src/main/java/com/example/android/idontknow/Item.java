package com.example.android.idontknow;

import java.io.Serializable;

/**
 * Created by aswin on 1/4/16.
 */
public class Item implements Serializable {
    private String itemname;
    private String thumbnailUrl;

    public Item(){

    }


    public String getItemname() {
        return itemname;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }


    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }


}
