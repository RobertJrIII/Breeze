package com.the3rdwheel.breeze

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.authentication.db.entity.Account
import com.the3rdwheel.breeze.reddit.RedditUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okio.IOException
import timber.log.Timber

class ViewModel(private val auth: Auth, private val database: AccountDatabase) : ViewModel() {


    fun setUser() {
        CoroutineScope(IO).launch {



            if (database.accountDao().getAnyUser() == null) {


                try {
                    val response =
                        auth.getAuthResponse(RedditUtils.CREDENTIALS)
                    database.accountDao()
                        .insert(
                            Account(
                                RedditUtils.ANONYMOUS_USER,
                                RedditUtils.ANONYMOUS_KARMA,
                                response,
                                RedditUtils.CURRENT_USER
                            )
                        )
                } catch (e: IOException) {
                    Timber.e(e)
                }


            }

        }
    }

    fun getAccessToken(): LiveData<Account>? {
        var data: LiveData<Account>? = null
        CoroutineScope(IO).launch {

            data = database.accountDao().getUserLiveData(RedditUtils.ANONYMOUS_USER)
        }

        return data
    }


}