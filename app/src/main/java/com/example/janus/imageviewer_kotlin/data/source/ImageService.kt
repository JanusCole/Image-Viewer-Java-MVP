package com.example.janus.imageviewer_kotlin.data.source

import com.example.janus.imageviewer_kotlin.data.models.ImageDescription

object ImageService : ImageServiceInterface {

    val remoteImageDataSource : RemoteImageDataSource = RemoteImageDataSource()

    // This is the image cache. It saves ImageDescription objects in a HashMap keyed on the image ID
    var imageCache: MutableMap<Int, ImageDescription> = mutableMapOf()

    // These fields are used to determine if the cache can satisfy the current image request
    var lastPageNumber = 0
    var totalImagesAvailable = 0
    var lastSearchCriteria : String = ""

    // Search the cache for a specific image using the image ID as a key
    override fun getImage(imageID: Int, imageSearchCallback: ImageServiceInterface.ImageSearchCallback) {
        if (imageCache.containsKey(imageID)) {
            imageSearchCallback.onImageFound(imageCache[imageID])
        } else {
            imageSearchCallback.onImageNotFound()
        }
    }

    override fun getImages(
        searchCriteria: String,
        requestedPageNumber: Int,
        imagesSearchCallback: ImageServiceInterface.ImagesSearchCallback
    ) {

        // Check to see if the cache can satisfy the current request
        if (isaNewRequest(searchCriteria, requestedPageNumber)) {

            // Check to see if we have reached the end of the available data
            if (endOfDataReached(searchCriteria)) {
                imagesSearchCallback.endOfDataReached()
            } else {

                // Search the data source using the passed search criteria and a page number
                remoteImageDataSource.getImages(
                    searchCriteria,
                    requestedPageNumber,
                    object : ImageServiceInterface.ImagesSearchCallback {
                        override fun onImagesFound(returnedImages: List<ImageDescription>, imageCount: Int) {

                            // Update the number of available images for this request
                            totalImagesAvailable = imageCount

                            // Load the newly retrieved ImageDescription objects to the cache
                            if (searchCriteria != lastSearchCriteria) {
                                lastSearchCriteria = searchCriteria
                                imageCache.clear()
                            }
                            for (returnedImage in returnedImages) {
                                imageCache.put(returnedImage.id, returnedImage)
                            }

                            // Update the most recently retrieved page number
                            lastPageNumber = requestedPageNumber

                            // Return the retrieved ImageDescription objects as a List
                            imagesSearchCallback.onImagesFound(returnedImages, imageCount)
                        }

                        override fun endOfDataReached() {
                            imagesSearchCallback.endOfDataReached()
                        }

                        override fun onNetworkError() {
                            imagesSearchCallback.onNetworkError()
                        }
                    })
            }
        } else {

            // If the cache can satisfy the current request, then send back the cached ImageDescription objects as a List
            val resultList: MutableList<ImageDescription> = ArrayList()
            for ((key, value) in imageCache.entries) {
                resultList.add(value)
            }
            imagesSearchCallback.onImagesFound(resultList, totalImagesAvailable)
        }
    }

    // Check to see if we have reached the last available image
    private fun endOfDataReached(searchCriteria: String): Boolean {
        return searchCriteria == lastSearchCriteria && imageCache.size >= totalImagesAvailable
    }

    // Check to see if the cache can satisfy the current request
    private fun isaNewRequest(searchCriteria: String, requestedPageNumber: Int): Boolean {
        return searchCriteria != lastSearchCriteria || requestedPageNumber > lastPageNumber
    }
}