package com.example.flickersearch

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickersearch.databinding.LayoutFlickerImageBinding
import com.example.flickersearch.models.Photo



class PhotosAdapter(private val viewModel: MainViewModel) : ListAdapter<Photo, PhotosAdapter.ViewHolder>(PhotosDiffUtilCallBack()) {
    private var page = 1
    class ViewHolder(private val binding: LayoutFlickerImageBinding,private val viewModel: MainViewModel):RecyclerView.ViewHolder(binding.root) {
        fun bind(item:Photo){
            binding.imgLogo.setImage(item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutFlickerImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding,viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))

        Log.i("RecyclerView","Position: $position Count :$itemCount")
        if (position == itemCount-1){
            Log.i("RecyclerView","If is triggered")
            page += 1
            viewModel.loadMore(page)
        }
    }
}

class PhotosDiffUtilCallBack:DiffUtil.ItemCallback<Photo>(){
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

}