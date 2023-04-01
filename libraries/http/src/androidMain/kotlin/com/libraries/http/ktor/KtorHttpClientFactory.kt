package com.libraries.http.ktor

import android.annotation.SuppressLint
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import java.io.EOFException
import java.net.ConnectException
import java.security.cert.X509Certificate
import javax.net.ssl.X509TrustManager

internal actual fun createKtorHttpClient(ignoreTLS: Boolean): HttpClient = HttpClient(CIO) {
    if (ignoreTLS) {
        engine {
            https {
                trustManager = @SuppressLint("CustomX509TrustManager") object : X509TrustManager {

                    @SuppressLint("TrustAllX509TrustManager")
                    @Suppress("EmptyFunctionBlock")
                    override fun checkClientTrusted(
                        p0: Array<out X509Certificate>?, p1: String?
                    ) {
                    }

                    @SuppressLint("TrustAllX509TrustManager")
                    @Suppress("EmptyFunctionBlock")
                    override fun checkServerTrusted(
                        p0: Array<out X509Certificate>?, p1: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate>? = null
                }
            }
        }
    }

    HttpResponseValidator {
        handleResponseExceptionWithRequest { exception, _ ->
            if (exception is ConnectException) {
                throw io.ktor.util.network.UnresolvedAddressException()
            }

            if (exception is ClosedReceiveChannelException) {
                throw io.ktor.util.network.UnresolvedAddressException()
            }

            if (exception is EOFException) {
                throw io.ktor.util.network.UnresolvedAddressException()
            }
        }
    }

}
