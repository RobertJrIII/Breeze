package com.the3rdwheel.breeze.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.the3rdwheel.breeze.model.AppOnlyResponse
import com.the3rdwheel.breeze.model.AppOnlyResponse_ID

@Dao
interface AppOnlyResponseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(apponlyresponse: AppOnlyResponse)

    @Update
    fun update(apponlyresponse: AppOnlyResponse)

    @Delete
    fun delete(apponlyresponse: AppOnlyResponse)

    @Query("select * from app_only_response_table where id = $AppOnlyResponse_ID")
    fun getOAuthResponse(): LiveData<AppOnlyResponse>
}