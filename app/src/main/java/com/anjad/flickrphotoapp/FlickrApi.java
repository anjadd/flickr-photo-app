package com.anjad.flickrphotoapp;

import com.anjad.flickrphotoapp.models.AllPhotos;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface FlickrApi {

    @GET("feeds/photos_public.gne")
    Single<AllPhotos> getFlickrPhotos(@QueryMap Map<String, String> filters);
}
