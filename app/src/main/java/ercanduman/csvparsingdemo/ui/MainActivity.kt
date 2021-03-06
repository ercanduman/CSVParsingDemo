package ercanduman.csvparsingdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import ercanduman.csvparsingdemo.R
import ercanduman.csvparsingdemo.data.model.Issue
import ercanduman.csvparsingdemo.data.source.LocalDataSource
import ercanduman.csvparsingdemo.databinding.ActivityMainBinding
import ercanduman.csvparsingdemo.util.Resource
import ercanduman.csvparsingdemo.util.exhaustive

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
                is Resource.Error -> displayViews(resource.message)
                is Resource.Success -> handleRetrievedData(resource.issues)
                is Resource.Loading -> binding.mainProgressBar.isVisible = true
            }.exhaustive
        }
    }

    private fun displayViews(errorMessage: String? = "") {
        binding.apply {
            mainProgressBar.isVisible = false
            mainErrorMessage.apply {
                isVisible = errorMessage.isNullOrEmpty().not()
                text = errorMessage
            }
            mainRecyclerView.isVisible = errorMessage.isNullOrEmpty()
        }
    }

    private fun handleRetrievedData(issues: List<Issue>?) {
        if (issues.isNullOrEmpty()) {
            displayViews(getString(R.string.no_data_found))
            return
        }
        displayViews(null)
        binding.apply {
            val issueAdapter = IssueAdapter().apply { submitList(issues) }

            mainRecyclerView.apply {
                isVisible = true
                setHasFixedSize(true) // Added for better performance optimizations.
                adapter = issueAdapter
            }
        }
    }
}