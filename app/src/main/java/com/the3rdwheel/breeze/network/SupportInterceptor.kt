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
        request =
            if (!SecurePreferences.contains(context, RedditUtils.SECRET_KEY)) {
                val token = retrieveToken()

                buildRequest(token, request)

            } else {
                val storedToken =
                    runBlocking(Default) {
                        SecurePreferences.getStringValue(
                            context,
                            RedditUtils.SECRET_KEY,
                            ""
                        )
                    }
                buildRequest(storedToken, request)
            }


        return chain.proceed(request)

    }

    private fun buildRequest(token: String?, request: Request): Request {

        if (token != "") {
            return request.newBuilder()
                .addHeader(
                    RedditUtils.AUTHORIZATION_KEY,
                    RedditUtils.AUTHORIZATION_BASE + token
                )
                .build()
        }
        return request
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
                    val newAccessToken = retrieveToken()
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


    private fun retrieveToken() = runBlocking(IO) {
        var accessToken: String? = ""
        val response = auth.getAuthResponse(RedditUtils.CREDENTIALS)

        if (response.isSuccessful && response.body() != null) {
            accessToken = response.body()!!.access_token

            withContext(Default) {
                SecurePreferences.setValue(
                    context,
                    RedditUtils.SECRET_KEY,
                    accessToken
                )
            }


        }

        return@runBlocking accessToken
    }


}