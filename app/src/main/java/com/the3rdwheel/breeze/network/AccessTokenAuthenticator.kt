package com.the3rdwheel.breeze.network

import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.reddit.authentication.db.entity.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.*
import okio.IOException
import timber.log.Timber
import java.lang.Exception


class AccessTokenAuthenticator(private val auth: Auth, private val database: AccountDatabase) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {
            val accessToken = response.request.header(RedditUtils.AUTHORIZATION_KEY)
                ?.substring(RedditUtils.AUTHORIZATION_BASE.length)

            synchronized(this) {
                val account = database.accountDao().getCurrentUser()


                val accessTokenFromDB = account.authResponse.access_token

                if (accessToken.equals(accessTokenFromDB)) {
                    val newAccessToken = refreshToken(account)
                    return if (newAccessToken != "") {

                        response.request.newBuilder().header(
                            RedditUtils.AUTHORIZATION_KEY,
                            RedditUtils.AUTHORIZATION_BASE + newAccessToken

                        ).build()

                    } else {
                        null
                    }
                } else {
                    return response.request.newBuilder().header(
                        RedditUtils.AUTHORIZATION_KEY,
                        RedditUtils.AUTHORIZATION_BASE + accessTokenFromDB

                    ).build()
                }
            }

        }


        return null
    }

    private fun refreshToken(account: Account): String? {
        var token: String = ""
        CoroutineScope(IO).launch {

            val response = auth.getAuthResponse(RedditUtils.CREDENTIALS)
            try {
                val accessToken = response.access_token
                database.accountDao().changeAccessToken(account.userName, accessToken)
                token = accessToken

            } catch (e: IOException) {
                Timber.e(e)
            }
        }


        return token

    }


}

//TODO If user logged in do something else
