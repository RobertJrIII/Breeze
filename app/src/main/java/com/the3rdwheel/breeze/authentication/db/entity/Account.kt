package com.the3rdwheel.breeze.authentication.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.the3rdwheel.breeze.authentication.network.response.AuthResponse

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = false) val userName: String,
    val karma: Int,
    @Embedded val authResponse: AuthResponse,
    val isCurrentUser: Boolean
)