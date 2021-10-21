package com.example.flickersearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rcrImageList.adapter = adapter
        binding.lifecycleOwner = this
        viewModel.setRequestQueue(Volley.newRequestQueue(applicationContext))
        viewModel.photoLiveList.observe(this,{
            adapter.submitList(it)
        })
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                p0?.also {
                    viewModel.fetchData(it)
                }
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }

        })

    }
}