package com.libraries.logger

import io.github.aakira.napier.Napier

class NapierLogger(
    override val tag: String? = null
) : Logger {
    override fun log(message: String, throwable: Throwable?, type: LogType) {
        type.toNapier(message = message, throwable = throwable, tag)
    }

}


private fun LogType.toNapier(
    message: String, throwable: Throwable? = null, tag: String? = null
) = when (this) {
    LogType.INFO -> Napier.i(message = message, throwable = throwable, tag = tag)
    LogType.VERBOSE -> Napier.v(message = message, throwable = throwable, tag = tag)
    LogType.ERROR -> Napier.e(message = message, throwable = throwable, tag = tag)
}
