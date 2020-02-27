package com.the3rdwheel.breeze.rawk

import com.the3rdwheel.breeze.rawk.authentication.api.Auth
import kotlinx.coroutines.withTimeoutOrNull

private const val JOB_TIMEOUT = 1200L
private const val FAILED_TOKEN_RETRIEVAL = "Failed to get access Token"

class Rawk(private val credentials: String) {


    private val auth = Auth.invoke()


    private suspend fun getS(): String? {

        return withTimeoutOrNull(JOB_TIMEOUT) {
                return@withTimeoutOrNull auth.getAuthResponse(credentials).access_token
            } ?: FAILED_TOKEN_RETRIEVAL

    }
}