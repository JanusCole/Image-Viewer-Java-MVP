package com.example.janus.imageviewer_kotlin.MultipleImageDisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.janus.imageviewer_kotlin.data.models.ImageDescription
import com.example.janus.imageviewer_kotlin.data.source.ImageService
import com.example.janus.imageviewer_kotlin.data.source.ImageServiceInterface

class MultipleImageDisplayViewModel : ViewModel() {

    private val images = MutableLiveData<MutableList<ImageDescription>>()

    fun getImages() : MutableLiveData<MutableList<ImageDescription>> {
        return images
    }

    fun loadImages(searchCriteria: String) {
        ImageService.getImages(searchCriteria, 1, object : ImageServiceInterface.ImagesSearchCallback {
            override fun onImagesFound(returnedImages: List<ImageDescription>, imageCount: Int) {
                if (returnedImages.isEmpty()) {
                    //multipleImageDisplayView.DisplayNetworkErrorMessage()
                } else {
                    images.value = returnedImages as MutableList<ImageDescription>
                }
            }

            override fun endOfDataReached() {
            }

            override fun onNetworkError() {
                //multipleImageDisplayView.DisplayNetworkErrorMessage()
            }
        })
    }
}