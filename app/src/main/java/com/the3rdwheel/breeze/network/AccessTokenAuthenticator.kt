package com.the3rdwheel.breeze.network

import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.authentication.api.Auth
import com.the3rdwheel.breeze.reddit.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.reddit.authentication.db.entity.Account
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class AccessTokenAuthenticator(private val auth: Auth, private val database: AccountDatabase) :
    Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        var currentUser: Account? = null
        if (response.code == 401) {

            CoroutineScope(IO).launch {
                currentUser = database.accountDao().getCurrentUser()

                if (currentUser?.userName.equals(RedditUtils.ANONYMOUS_USER)) {

                    val newAccessToken = auth.getAuthResponse(RedditUtils.CREDENTIALS).access_token
                    currentUser?.userName?.let {
                        database.accountDao()
                            .changeAccessToken(
                                it,
                                newAccessToken
                            )
                    }
                }
                //TODO If user logged in do something else


            }


            return response.request.newBuilder().header(
                RedditUtils.AUTHORIZATION_KEY,
                RedditUtils.AUTHORIZATION_BASE + currentUser?.authResponse?.access_token
            )
                .build()
        }


        return null
    }
}