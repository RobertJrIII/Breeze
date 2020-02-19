package com.the3rdwheel.breeze.api

import com.the3rdwheel.breeze.BuildConfig
import com.the3rdwheel.breeze.model.AppOnlyResponse
import okhttp3.Credentials
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface Auth {

    @FormUrlEncoded
    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    @POST("access_token")
    suspend fun getAppOnlyOathToken(
        @Header("Authorization") credentials: String,
        @Field("grant_type") grant_Type: String = "https://oauth.reddit.com/grants/installed_client",
        @Field("device_id") device_Id: String = "DO_NOT_TRACK_THIS_DEVICE"

    ): AppOnlyResponse


    companion object {
        val CREDENTIALS: String = Credentials.basic(BuildConfig.CLIENT_ID, "")
        operator fun invoke(): Auth {

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl("https://www.reddit.com/api/v1/")
                .addConverterFactory(MoshiConverterFactory.create())


            return retrofitBuilder.build().create(Auth::class.java)
        }

    }
}