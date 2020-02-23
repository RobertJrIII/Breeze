package com.the3rdwheel.breeze

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.room.RoomDatabase
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
    init {
        setUser()
    }

    private fun setUser() {
        CoroutineScope(IO).launch {

            if (database.isOpen) return@launch
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

    fun getAccessToken(): Account {
        return database.accountDao().getUser(RedditUtils.ANONYMOUS_USER)

    }

    override fun onCleared() {
        super.onCleared()

        if (database.isOpen) {
            database.close()
        }
    }
}