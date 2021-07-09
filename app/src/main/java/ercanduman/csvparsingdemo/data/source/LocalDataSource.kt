package ercanduman.csvparsingdemo.data.source

import android.content.Context
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

            if (CSV_SKIP_FIRST_LINE) lines.skip(1) // Skips 1. line of file which contains only headers.

            val issueList = mutableListOf<Issue>()
            var lineCounter = 1
            for (line in lines) {
                lineCounter++

                val fields = line.split(CSV_LINE_SEPARATOR)
                if (fields.size != CSV_LINE_FIELD_SIZE) {
                    emit(Resource.Error("#$lineCounter line of CSV file has invalid number of field. Line should contain at least $CSV_LINE_FIELD_SIZE items."))
                    continue
                }

                val issueCount = getIssueCounter(fields[2])
                if (issueCount == INVALID_NUMBER) {
                    emit(Resource.Error("#$lineCounter line of CSV file has invalid Integer number for Issue Count. Found value is ${fields[2]}"))
                    continue
                }

                val firstName = fields[0]
                val surname = fields[1]
                val dateOfBirth = fields[3]

                issueList.add(Issue(firstName, surname, issueCount, dateOfBirth))
            }

            emit(Resource.Success(issueList))
        } catch (e: Exception) {
            emit(Resource.Error("Error occurred when file read. Error: ${e.message}"))
        }
    }

    private suspend fun getFileLines() = withContext(Dispatchers.IO) {
        context.assets.open(CSV_FILE_NAME).bufferedReader().lines()
    }

    private fun getIssueCounter(countText: String): Int =
        try {
            countText.toInt()
        } catch (e: NumberFormatException) {
            INVALID_NUMBER
        }
}