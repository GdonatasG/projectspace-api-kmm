package com.libraries.logger

class PrintLogger(
    override val tag: String? = null
) : Logger {
    override fun log(message: String, throwable: Throwable?, type: LogType) {
        val fullMessage = if (throwable != null) {
            "$message\n${throwable.message}"
        } else {
            message
        }

        if (tag != null) {
            println("$tag - $fullMessage")

            return
        }

        println(fullMessage)
    }

}
