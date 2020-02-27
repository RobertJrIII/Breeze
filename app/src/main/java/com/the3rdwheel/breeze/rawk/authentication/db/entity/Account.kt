package com.the3rdwheel.breeze.rawk.authentication.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.the3rdwheel.breeze.rawk.authentication.network.response.AuthResponse

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = false) val userName: String,
    val karma: Int,
    @Embedded (prefix = "auth_") val authResponse: AuthResponse,
    val currentUser: Int
)