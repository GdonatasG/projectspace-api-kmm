package com.libraries.utils

expect class DateTimeFormatter() {
    fun stringUTC0(timestamp: Double, pattern: String): String
    fun stringLocalTimezone(timestamp: Double, pattern: String): String
    fun localTimezone(date: String, pattern: String): String

    companion object {
        val shared: DateTimeFormatter
    }
}
