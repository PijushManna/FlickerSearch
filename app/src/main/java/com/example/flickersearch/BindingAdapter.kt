package com.example.flickersearch

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickersearch.models.Photo

@BindingAdapter("set_image")
fun ImageView.setImage(item: Photo?){
    item?.also{
        val url = "https://live.staticflickr.com/${item.server}/${item.id}_${item.secret}.jpg"
        Glide.with(this.context)
            .load(url)
            .placeholder(R.drawable.loading)
            .into(this)
    }
}

@BindingAdapter("set_list")
fun RecyclerView.setList(items:List<Photo>?){
    items?.also {
        (adapter as PhotosAdapter).submitList(it)
    }
}