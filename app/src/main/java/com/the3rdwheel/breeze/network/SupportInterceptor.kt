package com.the3rdwheel.breeze.network

import android.content.Context
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import de.adorsys.android.securestoragelibrary.SecurePreferences
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
import okhttp3.*

class SupportInterceptor(private val auth: Auth, private val context: Context) : Authenticator,
    Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {

        var request = chain.request()

        if (!SecurePreferences.contains(context, RedditUtils.SECRET_KEY)) {
            val token = retrieveToken()
            if (token != "") {
                request = buildRequest(token, request)
//                request = request.newBuilder().addHeader(
//                    RedditUtils.AUTHORIZATION_KEY,
//                    RedditUtils.AUTHORIZATION_BASE + token
//                ).build()
            }


        } else {
            val storedToken = getStoredToken()
            request = buildRequest(storedToken, request)
//            request = request.newBuilder().addHeader(
//                RedditUtils.AUTHORIZATION_KEY,
//                RedditUtils.AUTHORIZATION_BASE + storedToken
//            ).build()
        }


        return chain.proceed(request)

    }

    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {
            val request = response.request

            val accessToken = request.header(RedditUtils.AUTHORIZATION_KEY)
                ?.substring(RedditUtils.AUTHORIZATION_BASE.length)

            synchronized(this) {
                val storedAccessToken = getStoredToken()


                if (storedAccessToken == "") return null

                if (accessToken == storedAccessToken) {
                    val newAccessToken = retrieveToken()
                    return if (newAccessToken != "") {
                        buildRequest(newAccessToken, request)
//                        request.newBuilder().header(
//                            RedditUtils.AUTHORIZATION_KEY,
//                            RedditUtils.AUTHORIZATION_BASE + newAccessToken
//
//                        ).build()

                    } else {
                        null
                    }
                } else {
                    return buildRequest(storedAccessToken, request)
//                    return request.newBuilder().header(
//                        RedditUtils.AUTHORIZATION_KEY,
//                        RedditUtils.AUTHORIZATION_BASE + storedAccessToken
//                    )
//                        .build()
                }
            }

        }


        return null
    }

    private fun buildRequest(token: String?, request: Request): Request {
        if (token != "") {
            return request.newBuilder().header(
                RedditUtils.AUTHORIZATION_KEY,
                RedditUtils.AUTHORIZATION_BASE
                        + token
            ).build()
        }
        return request
    }

    private fun retrieveToken() = runBlocking(IO) {
        var accessToken: String? = ""
        val response = auth.getAuthResponse(RedditUtils.CREDENTIALS)

        if (response.isSuccessful && response.body() != null) {
            accessToken = response.body()!!.access_token

            SecurePreferences.setValue(
                context,
                RedditUtils.SECRET_KEY,
                accessToken
            )
        }

        return@runBlocking accessToken
    }

    private fun getStoredToken() =
        SecurePreferences.getStringValue(
            context,
            RedditUtils.SECRET_KEY,
            ""
        )


}