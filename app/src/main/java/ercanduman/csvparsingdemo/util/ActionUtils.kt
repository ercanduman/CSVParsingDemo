package ercanduman.csvparsingdemo.util

/**
 * Stores utility functions for action within project.
 *
 * @author ercanduman
 * @since  10.07.2021
 */

/**
 * Turns statements into expressions. If this "exhaustive" value is used with 'when' expression, it makes
 * sure that all remaining branches handled, otherwise, gives compile time error.
 */
val <T> T.exhaustive: T get() = this