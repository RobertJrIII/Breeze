package com.the3rdwheel.breeze.rawk.authentication.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.the3rdwheel.breeze.rawk.authentication.db.entity.Account

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(account: Account)

    @Query("Update accounts Set auth_access_token =:accessToken Where userName =:userName")
    fun changeAccessToke(userName: String, accessToken: String)

    @Query("Select * From accounts Where userName =:userName")
    fun getUserLiveData(userName: String): LiveData<Account>


    @Query("Select * From accounts Where userName =:userName")
    fun getUser(userName: String): Account

    @Query("Delete From accounts Where userName =:userName")
    fun deleteUser(userName: String)

    @Query("Delete From accounts")
    fun deleteAllUsers()

    @Query("Update accounts Set karma =:karma Where userName =:userName")
    fun updateAccountKarma(userName: String, karma: Int)

    @Query("Select * From accounts LIMIT 1")
    fun getAnyUser(): Account?
}