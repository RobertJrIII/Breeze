package com.the3rdwheel.breeze.network

import android.content.Context
import android.content.Context.MODE_PRIVATE
import at.favre.lib.armadillo.Armadillo
import at.favre.lib.armadillo.ArmadilloSharedPreferences
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
        val securePrefs = Armadillo.create(
            context.getSharedPreferences(
                RedditUtils.SECURE_PREFS,
                MODE_PRIVATE
            )
        ).encryptionFingerprint(context.applicationContext).build()

        val token = securePrefs.getString(RedditUtils.SECRET_KEY, "")

        if (token != "") {
            request = request.newBuilder()
                .addHeader(RedditUtils.AUTHORIZATION_KEY, RedditUtils.AUTHORIZATION_BASE + token)
                .build()
        }


        return chain.proceed(request)
    }


    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {

            val accessToken = response.request.header(RedditUtils.AUTHORIZATION_KEY)
                ?.substring(RedditUtils.AUTHORIZATION_BASE.length)

            synchronized(this) {
                val securePrefs = Armadillo.create(
                    context.getSharedPreferences(
                        RedditUtils.SECURE_PREFS,
                        MODE_PRIVATE
                    )
                ).encryptionFingerprint(context.applicationContext).build()

                val storedAccessToken = securePrefs.getString(RedditUtils.SECRET_KEY, "")

                if (storedAccessToken == "") return null

                if (accessToken.equals(storedAccessToken)) {
                    val newAccessToken = refreshToken(securePrefs)
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


    private fun refreshToken(securePrefs: ArmadilloSharedPreferences) = runBlocking {
        val response = auth.getAuthResponse(RedditUtils.CREDENTIALS)

        val accessToken = response.access_token

        withContext(Main) {


            securePrefs.edit().putString(RedditUtils.SECRET_KEY, accessToken).apply()

        }


        return@runBlocking accessToken
    }


}