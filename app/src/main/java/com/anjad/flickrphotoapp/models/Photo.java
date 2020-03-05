package com.anjad.flickrphotoapp.models;

import com.squareup.moshi.Json;

public class Photo {

    private String title;

    @Json(name = "media")
    private SmallPhoto smallPhoto;

    public Photo(){}

    public Photo(String title, SmallPhoto smallPhoto) {
        this.title = title;
        this.smallPhoto = smallPhoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public SmallPhoto getSmallPhoto() {
        return smallPhoto;
    }

    public void setSmallPhoto(SmallPhoto smallPhoto) {
        this.smallPhoto = smallPhoto;
    }

    public String getLargePhotoLink(){
        //Small link: https://live.staticflickr.com/65535/49621094816_b84973c499_m.jpg
        String link = getSmallPhoto() != null ? getSmallPhoto().getSmallPhotoLink() : null;
        if (link != null) {
            link = link.replace("_m.jpg", "_b.jpg");
        }
        /*
        Second option:
        if (link != null && link.length() > 0 && link.charAt(link.length() - 5) == 'm') {
            link = link.substring(0, link.length() - 5);
            link = link.concat("b.jpg");
        }
        */
        return link;
    }



}
