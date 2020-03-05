package com.anjad.flickrphotoapp.models;

import com.squareup.moshi.Json;

import java.util.List;

public class AllPhotos {

    @Json(name = "items")
    private List<Photo> photos;

    public AllPhotos() {
    }

    public AllPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }
}
