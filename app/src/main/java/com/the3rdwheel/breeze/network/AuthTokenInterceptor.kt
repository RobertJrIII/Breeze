package com.the3rdwheel.breeze.network

import android.content.Context
import at.favre.lib.armadillo.Armadillo
import com.the3rdwheel.breeze.reddit.RedditUtils
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val securePrefs = Armadillo.create(
            context.getSharedPreferences(
                "secret_shared_prefs",
                Context.MODE_PRIVATE
            )
        ).encryptionFingerprint(context).build()
        val token = securePrefs.getString("Secret", "")

        if(token.isNullOrEmpty()) return chain.proceed(chain.request())

        val newRequest = chain.request().newBuilder().addHeader(
            RedditUtils.AUTHORIZATION_KEY,
            RedditUtils.AUTHORIZATION_BASE +
                    token
        ).build()



        return chain.proceed(newRequest)
    }
}