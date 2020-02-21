package com.the3rdwheel.breeze.authentication.db

import com.the3rdwheel.breeze.authentication.network.response.AuthResponse

data class Account(val userName: String,
                   val karma: Int,
                   val authResponse: AuthResponse)