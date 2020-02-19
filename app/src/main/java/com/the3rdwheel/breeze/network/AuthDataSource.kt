package com.the3rdwheel.breeze.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.response.AuthResponse
import okio.IOException
import timber.log.Timber


interface AuthDataSource {
    val downloadedAuthResponse: LiveData<AuthResponse>


    suspend fun fetchAuthResponse(credentials: String)
}


class AuthDataSourceImp(private val auth: Auth) : AuthDataSource {
    private val _downloadedAuthResponse = MutableLiveData<AuthResponse>()
    override val downloadedAuthResponse: LiveData<AuthResponse>
        get() = _downloadedAuthResponse

    override suspend fun fetchAuthResponse(credentials: String) {
        try {
            val response = auth.getAuthResponse(credentials)

            _downloadedAuthResponse.postValue(response)
        } catch (e: IOException) {
            Timber.e("No network")
        }
    }

}

