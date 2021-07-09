package ercanduman.csvparsingdemo.data.model

/**
 * Data holder class to store and hold issue related data.
 *
 * @author ercanduman
 * @since  09.07.2021
 */
data class Issue(
    val firstname: String = "",
    val surname: String = "",
    val issueCount: Int = 0,
    val dateOfBirth: String = ""
)