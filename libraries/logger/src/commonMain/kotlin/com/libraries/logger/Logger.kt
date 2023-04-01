package com.libraries.logger

interface Logger {
    val tag: String?

    fun log(
        message: String, throwable: Throwable? = null, type: LogType = LogType.INFO
    )
}

enum class LogType {
    INFO, ERROR, VERBOSE
}
