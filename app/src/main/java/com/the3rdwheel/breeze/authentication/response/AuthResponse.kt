package com.the3rdwheel.breeze.authentication.response

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json


@Entity(tableName = "auth_table")
data class AuthResponse(
    @PrimaryKey(autoGenerate = false)
    val user: String = "anonymous",
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String
)