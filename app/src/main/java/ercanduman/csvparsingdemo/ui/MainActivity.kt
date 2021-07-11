package ercanduman.csvparsingdemo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import ercanduman.csvparsingdemo.data.model.Issue
import ercanduman.csvparsingdemo.data.source.LocalDataSource
import ercanduman.csvparsingdemo.databinding.ActivityMainBinding
import ercanduman.csvparsingdemo.util.Resource
import ercanduman.csvparsingdemo.util.exhaustive

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var issueAdapter: IssueAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        issueAdapter = IssueAdapter()
        val factory = MainViewModelFactory(LocalDataSource(this))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        getFileData()
    }

    private fun getFileData() {
        viewModel.getData()
        viewModel.resourceLiveData.observe(this) { resource ->
            when (resource) {
                is Resource.Error -> handleException(resource.message)
                is Resource.Success -> handleRetrievedData(resource.issues)
                is Resource.Loading -> binding.mainProgressBar.isVisible = true
            }.exhaustive
        }
    }

    private fun handleException(message: String) {
        binding.apply {
            mainProgressBar.isVisible = false
            mainErrorMessage.apply {
                isVisible = true
                text = message
            }
        }
    }

    private fun handleRetrievedData(issues: List<Issue>) {
        issueAdapter.submitList(issues)
        binding.apply {
            mainProgressBar.isVisible = false
            mainRecyclerView.apply {
                isVisible = true
                setHasFixedSize(true) // Added for better performance optimizations.
                adapter = issueAdapter
            }
        }
    }
}