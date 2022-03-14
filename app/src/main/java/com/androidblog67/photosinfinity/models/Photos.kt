package com.androidblog67.photosinfinity.models

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Photos (
    @SerializedName("page")
    @Expose
    var page: Int? = null,

    @SerializedName("pages")
    @Expose
    var pages: Int? = null,

    @SerializedName("perpage")
    @Expose
    var perpage: Int? = null,

    @SerializedName("total")
    @Expose
    var total: Int? = null,

    @SerializedName("photo")
    @Expose
    var photo: List<Photo>? = null
)