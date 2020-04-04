package com.the3rdwheel.breeze.network

import com.the3rdwheel.breeze.reddit.RedditUtils
import okhttp3.Interceptor
import okhttp3.Response

class BaseHeader : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        request.newBuilder().addHeader(
            "Content-Type",
            "application/json"
        ).build().newBuilder().addHeader(
            "User-Agent",
            RedditUtils.USER_AGENT
        ).build()
        return chain.proceed(request)
    }
}