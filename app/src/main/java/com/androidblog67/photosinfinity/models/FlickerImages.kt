package com.androidblog67.photosinfinity.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FlickerImages(
    @SerializedName("photos")
    @Expose
    var photos: Photos? = null,

    @SerializedName("stat")
    @Expose
    var stat: String? = null
)