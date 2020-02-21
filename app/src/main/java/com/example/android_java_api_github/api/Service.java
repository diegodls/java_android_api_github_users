package com.example.android_java_api_github.api;

import com.example.android_java_api_github.model.ItemResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by DiegoL on 2/20/20
 */

public interface Service {

    @GET("/search/users?q=language:java&per_page=100")


    Call<ItemResponse> getItems();

}
