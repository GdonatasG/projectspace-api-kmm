package com.libraries.utils

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Locale

actual class DateTimeFormatter {
    actual fun stringUTC0(timestamp: Double, pattern: String): String {
        val formatter = java.time.format.DateTimeFormatter.ofPattern(pattern)
            .withLocale(Locale.getDefault())
            .withZone(
                ZoneId.systemDefault()
            )

        val date = LocalDateTime.ofEpochSecond(timestamp.toLong(), 0, ZoneOffset.UTC)

        return formatter.format(date)
    }

    actual fun stringLocalTimezone(timestamp: Double, pattern: String): String {
        val formatter = java.time.format.DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.systemDefault())

        val date = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.toLong()), ZoneId.systemDefault())

        return formatter.format(date)
    }

    actual fun localTimezone(date: String, pattern: String): String {
        val formatter = java.time.format.DateTimeFormatter
            .ofPattern(pattern)
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault())

        val timestamp = LocalDateTime.parse(date, formatter).toInstant(ZoneOffset.UTC).epochSecond

        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())

        return formatter.format(dateTime)
    }

    actual fun localTimezoneConverted(date: String, fromPattern: String, toPattern: String): String {
        val fromFormatter = java.time.format.DateTimeFormatter
            .ofPattern(fromPattern)
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault())

        val timestamp = LocalDateTime.parse(date, fromFormatter).toInstant(ZoneOffset.UTC).epochSecond

        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneId.systemDefault())

        val toFormatter = java.time.format.DateTimeFormatter
            .ofPattern(toPattern)
            .withLocale(Locale.getDefault())
            .withZone(ZoneId.systemDefault())

        return toFormatter.format(dateTime)
    }

    actual companion object {
        actual val shared: DateTimeFormatter by lazy {
            DateTimeFormatter()
        }
    }
}
