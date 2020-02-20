package com.the3rdwheel.breeze.authentication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.the3rdwheel.breeze.authentication.response.AuthResponse


@Dao
interface AuthResponseDao {

    @Insert
    fun insert(authResponse: AuthResponse)

    @Update
    fun updateAccessToken(user: String, accessToken: String)


    @Query("Select* From auth_table Where user =:user")
    fun getUser(user: String):AuthResponse

}