package com.the3rdwheel.breeze.rawk.authentication.network.response


data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String
)