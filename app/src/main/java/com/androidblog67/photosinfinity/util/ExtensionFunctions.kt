package com.androidblog67.photosinfinity.util

import com.androidblog67.photosinfinity.models.Photo

fun List<Photo>.toUrl(): List<String>{
    return map{ item ->
            "https://live.staticflickr.com/${item.server}/${item.id}_${item.secret}.jpg"
    }
}