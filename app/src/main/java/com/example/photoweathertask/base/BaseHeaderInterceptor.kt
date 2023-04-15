package com.example.photoweathertask.base

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class BaseHeaderInterceptor @Inject constructor(
) : Interceptor {
    companion object {
        private const val CONTENT_TYPE_KEY = "Accept"
        private const val CONTENT_TYPE_VALUE = "application/json"
        private const val AUTHORIZATION = "Authorization"
        private const val LOCALE = "locale"
    }

    override fun intercept(chain: Interceptor.Chain): Response = chain.proceed(
            chain.request().newBuilder()
                .addHeader(CONTENT_TYPE_KEY, CONTENT_TYPE_VALUE)
                .build()

    )
}