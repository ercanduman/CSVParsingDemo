package ercanduman.csvparsingdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import ercanduman.csvparsingdemo.R
import ercanduman.csvparsingdemo.data.source.LocalDataSource
import ercanduman.csvparsingdemo.util.Resource

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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