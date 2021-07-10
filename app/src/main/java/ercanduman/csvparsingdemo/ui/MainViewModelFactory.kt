package ercanduman.csvparsingdemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ercanduman.csvparsingdemo.data.source.LocalDataSource

/**
 * Creates instance of [MainViewModel] via providing its paramater objects.
 *
 * @author ercanduman
 * @since  10.07.2021
 */
class MainViewModelFactory(private val dataSource: LocalDataSource) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dataSource) as T
        } else throw IllegalArgumentException("Invalid class found as $modelClass")
    }
}