package com.the3rdwheel.breeze.network

import android.content.Context
import at.favre.lib.armadillo.Armadillo
import at.favre.lib.armadillo.ArmadilloSharedPreferences
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*

class SupportInterceptor(private val auth: Auth, private val context: Context) : Authenticator,
    Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val securePrefs = Armadillo.create(
            context.getSharedPreferences(
                RedditUtils.SECURE_PREFS,
                Context.MODE_PRIVATE
            )
        ).encryptionFingerprint(context.applicationContext).build()

        val token = securePrefs.getString(RedditUtils.AUTH_KEY, "")

        if (!token.isNullOrEmpty()) {
            request = request.newBuilder()
                .addHeader(RedditUtils.AUTHORIZATION_KEY, RedditUtils.AUTHORIZATION_BASE + token)
                .build()
        }


        return chain.proceed(request)
    }


    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {

            val securePrefs = Armadillo.create(
                context.getSharedPreferences(
                    RedditUtils.SECURE_PREFS,
                    Context.MODE_PRIVATE
                )
            ).encryptionFingerprint(context.applicationContext).build()


            val accessToken = response.request.header(RedditUtils.AUTHORIZATION_KEY)
                ?.substring(RedditUtils.AUTHORIZATION_BASE.length)

            synchronized(this) {


                val storedAccessToken = securePrefs.getString(RedditUtils.AUTH_KEY, "")

                if (storedAccessToken.isNullOrEmpty()) return null

                if (storedAccessToken == accessToken) {
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

                ).build()
            }

        }


        return null
    }

    private fun refreshToken(securePrefs: ArmadilloSharedPreferences): String? {
        var token: String? = ""
        CoroutineScope(IO).launch {


            val response = auth.getAuthResponse(RedditUtils.CREDENTIALS)

            val accessToken = response.access_token

            withContext(Main) {
                securePrefs.edit().putString(RedditUtils.AUTH_KEY, accessToken).apply()

            }
            token = accessToken


        }


        return token

    }


}