package ercanduman.csvparsingdemo.data

/**
 * Contains constant variables that used in app.
 *
 * @author ercanduman
 * @since  09.07.2021
 */
object Constants {
    // CSV File related constant variables.
    const val CSV_FILE_NAME = "issues.csv"
    const val CSV_LINE_SEPARATOR = ","
    const val CSV_LINE_FIELD_SIZE = 4

    /* Skips 1. line of file which contains only headers such as "First name","Sur name","Issue count","Date of birth" */
    const val CSV_SKIP_FIRST_LINE = true

    const val INVALID_NUMBER = -1
}