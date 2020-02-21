package com.the3rdwheel.breeze.authentication.repository

import androidx.lifecycle.LiveData
import com.the3rdwheel.breeze.BuildConfig
import com.the3rdwheel.breeze.authentication.db.AuthDatabase
import com.the3rdwheel.breeze.authentication.db.AuthResponseDao
import com.the3rdwheel.breeze.authentication.network.AuthDataSource
import com.the3rdwheel.breeze.authentication.response.AuthResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials

class AuthRepository(private val authResponseDao: AuthResponseDao, private val authDataSource: AuthDataSource) {


    init {
        authDataSource.downloadedAuthResponse.observeForever {
            persistAuthResponse(it)
        }
    }

    suspend fun getAuthResponse(): LiveData<AuthResponse> {
        return withContext(Dispatchers.IO) {
            initAuthResponse()
            return@withContext authResponseDao.getUserResponse("anonymous")
        }
    }


    private fun persistAuthResponse(fetchedAuthResponse: AuthResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            authResponseDao.insert(fetchedAuthResponse)
        }
    }


    private suspend fun initAuthResponse(){
        authDataSource.fetchAuthResponse(Credentials.basic(BuildConfig.CLIENT_ID, ""))
    }
}