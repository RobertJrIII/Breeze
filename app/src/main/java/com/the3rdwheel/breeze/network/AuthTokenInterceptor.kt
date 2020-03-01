package com.the3rdwheel.breeze.network

import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.authentication.db.AccountDatabase
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(private val database: AccountDatabase) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder().addHeader(
            RedditUtils.AUTHORIZATION_KEY,
            RedditUtils.AUTHORIZATION_BASE +
                    database.accountDao().getCurrentUser().authResponse.access_token
        ).build()
        return chain.proceed(newRequest)
    }
}