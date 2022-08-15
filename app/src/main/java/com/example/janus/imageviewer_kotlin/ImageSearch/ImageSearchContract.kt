package com.example.janus.imageviewer_kotlin.ImageSearch

interface ImageSearchContract {

    interface View {
        fun displayImagesUI(searchCriteria : String) : Unit
        fun displayNotFoundMessage() : Unit
        fun DisplayNetworkErrorMessage() : Unit
    }

    interface Presenter {
        fun loadImages(searchCriteria : String) : Unit
    }
}