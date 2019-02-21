package com.example.android.appprealpha;

/**
 * Created by Pavlo on 07-Apr-18.
 */

public class GooglePlace {
    private String name;
    private String category;
    private String rating;
    private String open;
    private String imageURL;
    private String address;

    public GooglePlace() {
        this.name = "";
        this.setCategory("");
        this.rating = "";
        this.open = "";
        this.imageURL = "";
        this.address="";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
