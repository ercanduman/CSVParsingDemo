package ercanduman.csvparsingdemo.data.source

import android.content.Context
import ercanduman.csvparsingdemo.R
import ercanduman.csvparsingdemo.data.Constants.CSV_FILE_NAME
import ercanduman.csvparsingdemo.data.Constants.CSV_LINE_FIELD_SIZE
import ercanduman.csvparsingdemo.data.Constants.CSV_LINE_SEPARATOR
import ercanduman.csvparsingdemo.data.Constants.CSV_SKIP_FIRST_LINE
import ercanduman.csvparsingdemo.data.Constants.INVALID_NUMBER
import ercanduman.csvparsingdemo.data.model.Issue
import ercanduman.csvparsingdemo.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext

/**
 * Reads CSV file and parses its content.
 *
 * @author ercanduman
 * @since  09.07.2021
 */
class LocalDataSource(private val context: Context) {

    fun readFile() = flow {
        emit(Resource.Loading)
        try {
            // Reading a file should be done via IO dispatcher
            val lines = getFileLines()

            val issueList = mutableListOf<Issue>()
            var lineCounter = if (CSV_SKIP_FIRST_LINE) 1 else 0
            for (line in lines) {
                lineCounter++

                if (line.isBlank()) {
                    val message = getMessage(lineCounter, R.string.format_error_empty)

                    emit(Resource.Error(message))
                    return@flow
                }

                val fields = line.split(CSV_LINE_SEPARATOR)
                if (fields.size != CSV_LINE_FIELD_SIZE) {
                    val message = getMessage(
                        lineCounter,
                        R.string.format_error_invalid_field,
                        CSV_LINE_FIELD_SIZE.toString()
                    )

                    emit(Resource.Error(message))
                    return@flow
                }

                val issueCount = getIssueCounter(fields[2])
                if (issueCount == INVALID_NUMBER) {
                    val message = getMessage(
                        lineCounter,
                        R.string.format_error_invalid_integer,
                        fields[2]
                    )

                    emit(Resource.Error(message))
                    return@flow
                }

                val firstName = fields[0].replace("\"", "")
                val surname = fields[1].replace("\"", "")
                val dateOfBirth = fields[3].replace("\"", "")

                issueList.add(Issue(firstName, surname, issueCount, dateOfBirth))
            }

            emit(Resource.Success(issueList))
        } catch (e: Throwable) {
            val errorMessage = e.message ?: context.getString(R.string.unknown_error)
            emit(Resource.Error(context.getString(R.string.error_when_reading, errorMessage)))
        }
    }

    private fun getMessage(lineNumber: Int, resourceId: Int, optional: String? = "") =
        context.getString(resourceId, lineNumber, optional)

    private suspend fun getFileLines() = withContext(Dispatchers.IO) {
        val lines = context.assets
            .open(CSV_FILE_NAME)
            .bufferedReader()
            .use { it.readText() }
            .lines()

        if (CSV_SKIP_FIRST_LINE) {
            /* Skips 1. line of file which contains only headers such as "First name","Sur name","Issue count","Date of birth" */
            return@withContext lines.drop(1)
        }
        return@withContext lines
    }

    private fun getIssueCounter(countText: String): Int =
        try {
            countText.toInt()
        } catch (e: NumberFormatException) {
            INVALID_NUMBER
        }
}