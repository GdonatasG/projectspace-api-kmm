package com.libraries.utils

expect class DateTimeFormatter() {
    fun stringUTC0(timestamp: Double, pattern: String): String
    fun stringToUTC0Timestamp(date: String, pattern: String): Long
    fun stringLocalTimezone(timestamp: Double, pattern: String): String
    fun localTimezone(date: String, pattern: String): String
    fun localTimezoneConverted(date: String, fromPattern: String, toPattern: String): String

    companion object {
        val shared: DateTimeFormatter
    }
}
