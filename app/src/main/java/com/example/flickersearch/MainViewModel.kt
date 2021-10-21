package com.example.flickersearch

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.example.flickersearch.models.FlickerImages
import com.example.flickersearch.models.Photo
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val gson = GsonBuilder().create()
    private val photosList = ArrayList<Photo>()
    val photoLiveList = MutableLiveData<List<Photo>?>()
    var searchText: String? = null
    lateinit var myQueue:RequestQueue

    init {
        Log.i("ViewModel", "INIT")
    }
    fun setRequestQueue(queue:RequestQueue){
        myQueue = queue
    }
    fun fetchData(text: String){
        photosList.clear()
        searchText = text
        val url =
            "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&text=$text&page=1"
        val req =  StringRequest(url,
            {
                viewModelScope.launch {
                    gson.fromJson(it, FlickerImages::class.java).apply {
                        photos?.photo?.let { img ->
                            photosList.addAll(img)
                        }
                    }
                    photoLiveList.value = photosList.toList()
                }
            },
            {
                it.message?.let { msg ->
                    Log.e("Volley", msg)
                }
            }
        )
        myQueue.add(req)
    }

    fun loadMore(page: Int) {
        val url =
            "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&text=$searchText&page=$page"
        val req =  StringRequest(url,
            {
                viewModelScope.launch {
                    gson.fromJson(it, FlickerImages::class.java).apply {
                        Log.i("Volley : Photos", photos.toString())
                        photos?.photo?.forEach { img ->
                            Log.i("Volley", img.toString())
                            photosList.add(img)
                        }
                    }
                    photoLiveList.value = photosList.toList()
                }
            },
            {
                it.message?.let { msg ->
                    Log.e("Volley", msg)
                }
            }
        )

        myQueue.add(req)
    }
}