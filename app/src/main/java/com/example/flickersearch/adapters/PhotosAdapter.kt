package com.example.flickersearch.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flickersearch.MainViewModel
import com.example.flickersearch.R
import com.example.flickersearch.databinding.LayoutFlickerImageBinding
import com.example.flickersearch.models.Photo
import com.example.flickersearch.setImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class PhotosAdapter(private val viewModel: MainViewModel) : ListAdapter<Photo, PhotosAdapter.ViewHolder>(
    PhotosDiffUtilCallBack()
) {
    private var page = 1

    class ViewHolder(private val binding: LayoutFlickerImageBinding,private val viewModel: MainViewModel):RecyclerView.ViewHolder(binding.root) {
        private lateinit var context: Context
        fun bind(item:Photo){
            context = binding.root.context
            binding.tvDownload.text = context.getString(R.string.download_image)
            binding.imgLogo.setImage(item)
            binding.ibDownload.visibility = View.VISIBLE
            binding.ivDownloaded.visibility = View.GONE
            binding.ibDownload.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val url =
                        "https://live.staticflickr.com/${item.server}/${item.id}_${item.secret}.jpg"
                    val bitImg = Glide.with(context)
                        .asBitmap()
                        .load(url) // sample image
                        .submit()
                        .get()

                    withContext(Dispatchers.Main) {
                        binding.tvDownload.text = saveImage(bitImg, item)
                        binding.ibDownload.visibility = View.GONE
                        binding.ivDownloaded.visibility = View.VISIBLE
                    }
                }
            }
        }

        private fun saveImage(image: Bitmap,item:Photo): String? {
            var savedImagePath: String? = null
            val imageFileName = "${item.title}.jpg"
            val storageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    .toString() + "/FLICKER"
            )
            var success = true
            if (!storageDir.exists()) {
                success = storageDir.mkdirs()
            }
            if (success) {
                val imageFile = File(storageDir, imageFileName)
                savedImagePath = imageFile.absolutePath
                try {
                    val fOut: OutputStream = FileOutputStream(imageFile)
                    image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                    fOut.close()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

                // Add the image to the system gallery
                galleryAddPic(savedImagePath)
                //Toast.makeText(this, "IMAGE SAVED", Toast.LENGTH_LONG).show() // to make this working, need to manage coroutine, as this execution is something off the main thread
            }
            return savedImagePath
        }

        private fun galleryAddPic(imagePath: String?) {
            imagePath?.let { path ->
                val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                val f = File(path)
                val contentUri: Uri = Uri.fromFile(f)
                mediaScanIntent.data = contentUri
                context.sendBroadcast(mediaScanIntent)
            }
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