package com.example.photoweathertask.base

import com.example.photoweathertask.utils.validation.network.NetworkValidator
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

class ConnectionInterceptor @Inject constructor(private val networkValidator: NetworkValidator) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (networkValidator.isConnected())
            return chain.proceed(chain.request())
        else
            throw NoNetworkConnectionException()
    }
}

class NoNetworkConnectionException : IOException() {
    override val message: String
        get() = "No network available, please check your WiFi or Vehicle connection"
}