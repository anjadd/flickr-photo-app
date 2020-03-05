package com.anjad.flickrphotoapp.models;

import com.squareup.moshi.Json;

public class SmallPhoto {

    @Json(name = "m")
    private String smallPhotoLink;

    public SmallPhoto(String smallPhotoLink) {
        this.smallPhotoLink = smallPhotoLink;
    }

    public String getSmallPhotoLink() {
        return smallPhotoLink;
    }

    public void setSmallPhotoLink(String smallPhotoLink) {
        this.smallPhotoLink = smallPhotoLink;
    }
}

