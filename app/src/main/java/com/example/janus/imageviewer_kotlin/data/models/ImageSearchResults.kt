package com.example.janus.imageviewer_kotlin.data.models

import com.google.gson.annotations.SerializedName;

data class ImageSearchResults (
        @SerializedName("total") val total : Int,
        @SerializedName("totalHits") val totalHits : Int,
        @SerializedName("hits") val hits : List<ImageDescription>
)
