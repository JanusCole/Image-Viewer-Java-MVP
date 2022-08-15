package com.example.janus.imageviewer_kotlin.data.source

import com.example.janus.imageviewer_kotlin.data.models.ImageSearchResults
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface RemoteImageAPI {

    @GET("api/")
    fun getImages(@Query("key") apiKey: String,
                  @Query("q") searchCriteria: String,
                  @Query("page") pageNumber: Int)
            : Call<ImageSearchResults>;
}