package com.the3rdwheel.breeze.network

import android.content.Context
import at.favre.lib.armadillo.Armadillo
import at.favre.lib.armadillo.ArmadilloSharedPreferences
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.*


class AccessTokenAuthenticator(private val auth: Auth, private val context: Context) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {
            val accessToken = response.request.header(RedditUtils.AUTHORIZATION_KEY)
                ?.substring(RedditUtils.AUTHORIZATION_BASE.length)

            synchronized(this) {
                val securePrefs = Armadillo.create(
                    context.getSharedPreferences(
                        "secret_shared_prefs",
                        Context.MODE_PRIVATE
                    )
                ).encryptionFingerprint(context).build()


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


//            try {
                val response = async { auth.getAuthResponse(RedditUtils.CREDENTIALS) }
                val tokenResponse = response.await()
                if (tokenResponse.isSuccessful) {
                    val accessToken = tokenResponse.body()?.access_token
                    securePrefs.edit().putString(RedditUtils.AUTH_KEY, accessToken).apply()
                    token = accessToken
                }


//            } catch (e: IOException) {
//                Timber.e(e)
//            }
        }


        return token

    }


}

//TODO If user logged in do something else
