package com.libraries.utils

import platform.Foundation.*

actual class DateTimeFormatter {
    private val formatter: NSDateFormatter by lazy {
        NSDateFormatter()
    }

    actual fun stringUTC0(timestamp: Double, pattern: String): String {
        formatter.dateFormat = pattern
        formatter.timeZone = NSTimeZone.timeZoneForSecondsFromGMT(0)
        val date = NSDate.dateWithIntervalSince1970(timestamp)

        return formatter.stringFromDate(date)
    }

    actual fun stringLocalTimezone(timestamp: Double, pattern: String): String {
        formatter.dateFormat = pattern
        formatter.timeZone = NSTimeZone.localTimeZone
        val date = NSDate.dateWithIntervalSince1970(timestamp)
        return formatter.stringFromDate(date)
    }

    actual fun localTimezone(date: String, pattern: String): String {
        formatter.dateFormat = pattern
        formatter.timeZone = NSTimeZone.timeZoneForSecondsFromGMT(0)
        val adjustableDate = formatter.dateFromString(date)!!
        formatter.timeZone = NSTimeZone.localTimeZone
        return formatter.stringFromDate(adjustableDate)
    }

    actual fun localTimezoneConverted(date: String, fromPattern: String, toPattern: String): String {
        throw Exception("implement")
    }


    actual companion object {
        actual val shared: DateTimeFormatter by lazy {
            DateTimeFormatter()
        }
    }
}
