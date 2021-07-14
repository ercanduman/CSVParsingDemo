package ercanduman.csvparsingdemo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ercanduman.csvparsingdemo.data.source.LocalDataSource
import ercanduman.csvparsingdemo.util.Resource
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ViewModel is responsible to retrieve data and keep it for UI. ViewModel is lifecycle aware component
 * and survives configuration changes.
 *
 * @author ercanduman
 * @since  09.07.2021
 */
class MainViewModel(private val dataSource: LocalDataSource) : ViewModel() {

    private val _resourceMutableLiveData = MutableLiveData<Resource>()
    val resourceLiveData: LiveData<Resource> = _resourceMutableLiveData

    fun getData() = viewModelScope.launch {
        dataSource.readFile()
            .catch { error -> _resourceMutableLiveData.value = Resource.Error(error.message) }
            .collect { _resourceMutableLiveData.value = it }
    }
}