package com.example.janus.imageviewer_kotlin.data.source

import com.example.janus.imageviewer_kotlin.BuildConfig
import com.example.janus.imageviewer_kotlin.data.models.ImageSearchResults
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteImageDataSource {

    fun getImages (searchCriteria : String, pageNumber : Int, imagesSearchCallback : ImageServiceInterface.ImagesSearchCallback) : Unit {

        var formattedSearchCriteria : String = searchCriteria.replace(" ", "+")

        var retrofitBuilder : Retrofit.Builder = Retrofit.Builder ()
                .baseUrl("https://pixabay.com/")
                .addConverterFactory(GsonConverterFactory.create())

        var retrofit : Retrofit = retrofitBuilder.build()
        var imagesAPIClient : RemoteImageAPI = retrofit.create(RemoteImageAPI::class.java)
        var imageSearchCall : Call <ImageSearchResults> = imagesAPIClient.getImages(BuildConfig.APIKEY, formattedSearchCriteria, 1)

        imageSearchCall.enqueue(object: Callback<ImageSearchResults> {
            override fun onResponse(
                call: Call<ImageSearchResults>,
                response: Response<ImageSearchResults>
            ) {
                if (response.isSuccessful) {
                    imagesSearchCallback.onImagesFound(response.body()!!.hits, response.body()!!.totalHits)
                } else {
                    imagesSearchCallback.onNetworkError()
                }
            }
            override fun onFailure(call: Call<ImageSearchResults>, t: Throwable) {
                imagesSearchCallback.onNetworkError()
            }
        })

    }

}