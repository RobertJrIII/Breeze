package com.the3rdwheel.breeze.reddit.authentication.response

import androidx.annotation.Keep

@Keep
data class AuthResponse(
    val access_token: String
)