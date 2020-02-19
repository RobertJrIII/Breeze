package com.the3rdwheel.breeze.authentication.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.the3rdwheel.breeze.authentication.AuthResponse

@Dao
interface AuthResponseDao {

    @Insert()
    fun insert(apponlyresponse: AuthResponse)

    @Update
    fun update(apponlyresponse: AuthResponse)

    @Delete
    fun delete(apponlyresponse: AuthResponse)

    @Query("select * from auth_response_table where id = id")
    fun getOAuthResponse(id: String): LiveData<AuthResponse>
}