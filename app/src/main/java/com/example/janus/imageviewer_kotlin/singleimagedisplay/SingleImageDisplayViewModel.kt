package com.example.janus.imageviewer_kotlin.singleimagedisplay

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.janus.imageviewer_kotlin.data.models.ImageDescription
import com.example.janus.imageviewer_kotlin.data.source.ImageService
import com.example.janus.imageviewer_kotlin.data.source.ImageServiceInterface

class SingleImageDisplayViewModel : ViewModel() {

    private val image = MutableLiveData<ImageDescription>()

    fun getImage() : MutableLiveData<ImageDescription> {
        return image
    }

    // Go the the repository and request the specific ImageDescription object using the imageID.
    // When it is returned, call the view to display the data fields and call Picasso to get the image bitmap
    fun loadImage(imageIDToDisplay: Int) {
        ImageService.getImage(imageIDToDisplay, object : ImageServiceInterface.ImageSearchCallback {
            override fun onImageFound(returnedImage: ImageDescription?) {
                if (returnedImage == null) {
                    //multipleImageDisplayView.DisplayNetworkErrorMessage()
                } else {
                    image.value = returnedImage
                }
            }

            override fun onImageNotFound() {
                //singleImageDisplayView.displayImageNotFoundMessage()
            }
        })
    }

}