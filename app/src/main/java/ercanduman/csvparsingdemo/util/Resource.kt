package ercanduman.csvparsingdemo.util

import ercanduman.csvparsingdemo.data.model.Issue

/**
 * Handles execution status and returns respective result which can be Success, Error or Loading.
 *
 * @author ercanduman
 * @since  09.07.2021
 */
sealed class Resource {
    object Loading : Resource()
    data class Error(val message: String) : Resource()
    data class Success(val issues: List<Issue>) : Resource()
}