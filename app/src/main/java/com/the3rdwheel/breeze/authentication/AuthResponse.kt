package com.the3rdwheel.breeze.authentication

import androidx.room.Entity
import androidx.room.PrimaryKey

const val AppOnlyResponse_ID = 0

@Entity(tableName = "app_only_response_table")
data class AppOnlyResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: String,
    val scope: String
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = AppOnlyResponse_ID


}