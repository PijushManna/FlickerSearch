package com.example.flickersearch

import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.android.volley.toolbox.Volley
import com.example.flickersearch.adapters.PhotosAdapter
import com.example.flickersearch.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private val binding:ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private val adapter: PhotosAdapter by lazy {
        PhotosAdapter(viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.rcrImageList.adapter = adapter
        binding.lifecycleOwner = this
        binding.model = viewModel

        viewModel.setRequestQueue(Volley.newRequestQueue(applicationContext))
        viewModel.fetchData("random")
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