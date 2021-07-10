package ercanduman.csvparsingdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ercanduman.csvparsingdemo.data.source.LocalDataSource
import ercanduman.csvparsingdemo.databinding.ActivityMainBinding
import ercanduman.csvparsingdemo.util.Resource

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory(LocalDataSource(this))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        getFileData()
    }

    private fun getFileData() {
        viewModel.getData()
        viewModel.resourceLiveData.observe(this) { resource ->
            when (resource) {
                is Resource.Error -> TODO()
                is Resource.Success -> TODO()
                is Resource.Loading -> TODO()
            }
        }
    }
}