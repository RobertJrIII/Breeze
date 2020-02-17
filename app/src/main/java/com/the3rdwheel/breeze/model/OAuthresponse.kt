package com.the3rdwheel.breeze.model

data class OAuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String
)