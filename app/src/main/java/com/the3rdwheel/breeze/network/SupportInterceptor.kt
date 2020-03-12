package com.the3rdwheel.breeze.network

import android.content.Context
import android.content.Context.MODE_PRIVATE
import at.favre.lib.armadillo.Armadillo
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.*

class SupportInterceptor(private val auth: Auth, private val context: Context) : Authenticator,
    Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val storedToken = runBlocking(Main) {

            return@runBlocking Armadillo.create(
                    context.getSharedPreferences(
                        RedditUtils.SECURE_PREFS,
                        MODE_PRIVATE
                    )
                ).encryptionFingerprint(context.applicationContext).build()
                .getString(RedditUtils.SECRET_KEY, "")

        }



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
                val storedAccessToken = runBlocking(Main) {

                    return@runBlocking Armadillo.create(
                            context.getSharedPreferences(
                                RedditUtils.SECURE_PREFS,
                                MODE_PRIVATE
                            )
                        )
                        .encryptionFingerprint(context.applicationContext).build()
                        .getString(RedditUtils.SECRET_KEY, "")


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


    private fun refreshToken() = runBlocking {

        val response = auth.getAuthResponse(RedditUtils.CREDENTIALS)
        var accessToken: String? = ""

        if (response.isSuccessful && response.body() != null) {
            accessToken = response.body()?.access_token

            withContext(Main) {
                val securePrefs = Armadillo.create(
                    context.getSharedPreferences(
                        RedditUtils.SECURE_PREFS,
                        MODE_PRIVATE
                    )
                ).encryptionFingerprint(context.applicationContext).build()

                securePrefs.edit().putString(RedditUtils.SECRET_KEY, accessToken).apply()

            }
        }




        return@runBlocking accessToken
    }


}