package com.the3rdwheel.breeze.authentication.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.response.AuthResponse
import okio.IOException
import timber.log.Timber

class AuthDataSource(private val auth: Auth) {
    private val _downloadedAuthResponse = MutableLiveData<AuthResponse>()
    val downloadedAuthResponse: LiveData<AuthResponse>
        get() = _downloadedAuthResponse

    suspend fun fetchAuthResponse(credentials: String) = try {
        val response = auth.getAuthResponse(credentials)

        this._downloadedAuthResponse.postValue(response)
    } catch (e: IOException) {
        Timber.e("No network")
    }


}

