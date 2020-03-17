package com.the3rdwheel.breeze.network

import android.content.Context
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import de.adorsys.android.securestoragelibrary.SecurePreferences
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*

class SupportInterceptor(private val auth: Auth, private val context: Context) : Authenticator,
    Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        val storedToken =
            SecurePreferences.getStringValue(context.applicationContext, RedditUtils.SECRET_KEY, "")




        if (storedToken != "") {
            request = request.newBuilder()
                .addHeader(
                    RedditUtils.AUTHORIZATION_KEY,
                    RedditUtils.AUTHORIZATION_BASE + storedToken
                )
                .build()
        }


        return chain.proceed(request)

    }


    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {

            val accessToken = response.request.header(RedditUtils.AUTHORIZATION_KEY)
                ?.substring(RedditUtils.AUTHORIZATION_BASE.length)

            synchronized(this) {
                val storedAccessToken = runBlocking(Default) {
                    SecurePreferences.getStringValue(
                        context.applicationContext,
                        RedditUtils.SECRET_KEY,
                        ""
                    )
                }


                if (storedAccessToken == "") return null

                if (accessToken == storedAccessToken) {
                    val newAccessToken = refreshToken()
                    return if (newAccessToken != "") {

                        response.request.newBuilder().header(
                            RedditUtils.AUTHORIZATION_KEY,
                            RedditUtils.AUTHORIZATION_BASE + newAccessToken

                        ).build()

                    } else {
                        null
                    }
                } else return response.request.newBuilder().header(
                        RedditUtils.AUTHORIZATION_KEY,
                        RedditUtils.AUTHORIZATION_BASE + storedAccessToken
                    )
                    .build()
            }

        }


        return null
    }


    private fun refreshToken() = runBlocking(IO) {

        val response = auth.getAuthResponse(RedditUtils.CREDENTIALS)
        var accessToken: String? = ""

        if (response.isSuccessful && response.body() != null) {
            accessToken = response.body()!!.access_token

            SecurePreferences.setValue(
                context.applicationContext,
                RedditUtils.SECRET_KEY,
                accessToken
            )


        }

        return@runBlocking accessToken
    }


}