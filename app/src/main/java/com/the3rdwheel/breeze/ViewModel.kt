package com.the3rdwheel.breeze

import androidx.lifecycle.ViewModel
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.authentication.db.entity.Account
import com.the3rdwheel.breeze.reddit.ANONYMOUS_KARMA
import com.the3rdwheel.breeze.reddit.ANONYMOUS_USER
import com.the3rdwheel.breeze.reddit.CREDENTIALS
import com.the3rdwheel.breeze.reddit.CURRENT_USER
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import timber.log.Timber

class ViewModel(auth: Auth, database: AccountDatabase) : ViewModel() {
    private val auth = auth
    private val database = database
    fun setUser() {
        CoroutineScope(IO).launch {
            if (database.accountDao().getAnyUser() == null) {


                try {
                    val response =
                        auth.getAuthResponse(CREDENTIALS)
                    database.accountDao()
                        .insert(
                            Account(
                                ANONYMOUS_USER,
                                ANONYMOUS_KARMA,
                                response,
                                CURRENT_USER
                            )
                        )
                } catch (e: IOException) {
                    Timber.e(e)
                }


            }

        }
    }

    fun getAccessToken(): String? {
        var token: String? = null
        CoroutineScope(IO).launch {
            val response = database.accountDao().getUser(ANONYMOUS_USER).authResponse.access_token
            withContext(Dispatchers.Main) {
               return@withContext response
            }
        }

        return token
    }

}