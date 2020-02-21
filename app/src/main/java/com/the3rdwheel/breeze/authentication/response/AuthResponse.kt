package com.the3rdwheel.breeze.authentication.response

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "auth_table")
data class AuthResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String
) {
    @PrimaryKey()
    val user: String = "anonymous"
}