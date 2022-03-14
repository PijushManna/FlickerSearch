package com.androidblog67.photosinfinity.util

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.androidblog67.photosinfinity.adapters.PhotosAdapter
import com.androidblog67.photosinfinity.models.Photo

@BindingAdapter("set_image")
fun ImageView.setImage(item: Photo?){
    item?.also{
        val url = "https://live.staticflickr.com/${it.server}/${it.id}_${it.secret}.jpg"
        Log.i("Glide",it.server.toString())
        Log.i("Glide",it.id.toString())
        Log.i("Glide",it.secret.toString())
        Glide.with(this.context)
            .load(url)
            .placeholder(android.R.drawable.progress_indeterminate_horizontal) // need placeholder to avoid issue like glide annotations
            .error(android.R.drawable.stat_notify_error) // need error to avoid issue like glide annotations
            .into(this)
    }
}

@BindingAdapter("set_list")
fun RecyclerView.setList(items:List<Photo>?){
    items?.also {
        (adapter as PhotosAdapter).submitList(it)
    }
}