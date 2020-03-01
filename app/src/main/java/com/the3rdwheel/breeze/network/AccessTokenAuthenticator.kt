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


class AccessTokenAuthenticator(private val auth: Auth, private val database: AccountDatabase) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        if (response.code == 401) {
            Timber.d("response is 401 for some reason")
            val accessToken = response.request.header(RedditUtils.AUTHORIZATION_KEY)
                ?.substring(RedditUtils.AUTHORIZATION_BASE.length)

            synchronized(this) {
                val account = database.accountDao().getCurrentUser()


                val accessTokenFromDB = account.authResponse.access_token

                if (accessToken.equals(accessTokenFromDB)) {
                    val newAccessToken = refreshToken(account)
                    return if (newAccessToken != "") {

                        response.request.newBuilder().headers(
                            Headers.headersOf(
                                RedditUtils.AUTHORIZATION_KEY,
                                RedditUtils.AUTHORIZATION_BASE + newAccessToken
                            )
                        )
                            .build()

                    } else {
                        null
                    }
                } else {
                    return response.request.newBuilder().headers(
                        Headers.headersOf(
                            RedditUtils.AUTHORIZATION_KEY,
                            RedditUtils.AUTHORIZATION_BASE + accessTokenFromDB
                        )
                    ).build()
                }
            }

        }


        return null
    }

    private fun refreshToken(account: Account): String? {
        var newAccessToken: String? = ""

        try {
            CoroutineScope(IO).launch {

                newAccessToken =
                    auth.getAuthResponse(RedditUtils.CREDENTIALS).access_token
                if (!newAccessToken.isNullOrEmpty()) {
                    database.accountDao().changeAccessToken(account.userName, newAccessToken!!)
                }


            }


        } catch (e: IOException) {

            Timber.e(e)
        }

        return newAccessToken

    }


}


//
//CoroutineScope(IO).launch {
//    currentUser = database.accountDao().getCurrentUser()
//
//    if (currentUser?.userName.equals(RedditUtils.ANONYMOUS_USER)) {
//        try {
//
//            val newAccessToken =
//                auth.getAuthResponse(RedditUtils.CREDENTIALS).access_token
//            currentUser?.userName?.let {
//                database.accountDao()
//                    .changeAccessToken(
//                        it,
//                        newAccessToken
//                    )
//            }
//        } catch (e: IOException) {
//            Timber.e(e)
//        }
//    }
//    //TODO If user logged in do something else
//
//
//}