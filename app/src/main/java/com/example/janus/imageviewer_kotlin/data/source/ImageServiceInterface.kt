package com.example.janus.imageviewer_kotlin.data.source

import com.example.janus.imageviewer_kotlin.data.models.ImageDescription

interface ImageServiceInterface {

    fun getImage(imageID: Int, imageSearchCallback: ImageServiceInterface.ImageSearchCallback)
    fun getImages(
        searchCriteria: String,
        requestedPageNumber: Int,
        imagesSearchCallback: ImageServiceInterface.ImagesSearchCallback
    )

    interface ImagesSearchCallback {
        fun onImagesFound (returnedImages : List<ImageDescription>, imageCount : Int) : Unit
        fun endOfDataReached() : Unit
        fun onNetworkError() : Unit
    }

    interface ImageSearchCallback {
        fun onImageFound(returnedImage: ImageDescription?)
        fun onImageNotFound()
    }
}