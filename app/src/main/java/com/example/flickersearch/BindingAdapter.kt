package com.example.flickersearch

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickersearch.models.Photo

@BindingAdapter("set_image")
fun ImageView.setImage(item: Photo?){
    item?.also{
        val url = "http://farm${it.farm}.static.flickr.com/${it.server}/${it.id}_${it.secret}.jpg"
        val url2 = "https://picsum.photos/200/300"
        Glide.with(this.context).load(url2).into(this)
    }
}

@BindingAdapter("set_list")
fun RecyclerView.setList(items:List<Photo>?){
    items?.also {
        (adapter as PhotosAdapter).submitList(it)
    }
}