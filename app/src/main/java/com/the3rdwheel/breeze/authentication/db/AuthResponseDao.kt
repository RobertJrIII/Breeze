package com.the3rdwheel.breeze.authentication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.the3rdwheel.breeze.authentication.response.AuthResponse


@Dao
interface AuthResponseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(authResponse: AuthResponse)

    @Update
    fun updateAccessToken(user: String, accessToken: String)


    @Query("Select* From auth_table Where user =:user")
    fun getUserResponse(user: String): LiveData<AuthResponse>

    @Query("Select* From auth_table Where user =:user")
    fun getUserResponseLiveData(user: String): LiveData<AuthResponse>

    @Query("Delete From auth_table")
    fun removeAllResponses()
}