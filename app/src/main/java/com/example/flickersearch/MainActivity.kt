package com.example.flickersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley
import com.example.flickersearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel:MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel::class.java)
    }
    private val adapter:PhotosAdapter by lazy {
        PhotosAdapter(viewModel)
    }
    private lateinit var requestQueue:RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rcrImageList.adapter = adapter
        binding.lifecycleOwner = this
//        binding.model = viewModel

        requestQueue = Volley.newRequestQueue(applicationContext)
        requestQueue.add(viewModel.fetchData("tesla"))
        viewModel.photoLiveList.observe(this,{
            binding.textView.text = it?.size.toString()
            adapter.submitList(it)
        })
        var page = 2
        binding.button.setOnClickListener {
            requestQueue.add(viewModel.loadMore(page++))
        }
    }
}