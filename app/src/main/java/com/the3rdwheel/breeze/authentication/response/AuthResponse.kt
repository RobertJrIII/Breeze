package com.the3rdwheel.breeze.authentication.response

import androidx.room.Entity
import androidx.room.PrimaryKey


//@Entity(tableName = "auth_response_table")
data class AuthResponse(
   // @PrimaryKey(autoGenerate = false)
    val id: String = "anonymous",
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String
)