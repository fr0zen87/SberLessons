package com.example.lesson18.retrofit;

import com.example.lesson18.entities.Image;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ImageWebService {

    @Headers(value = {"Api-Key:???"})
    @GET("search/images?fields=id,title,thumb&sort_order=best/")
    Call<Image> getImages();
}
