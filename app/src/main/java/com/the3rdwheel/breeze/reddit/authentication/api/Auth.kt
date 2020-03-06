package com.the3rdwheel.breeze.reddit.authentication.api

import com.the3rdwheel.breeze.reddit.authentication.response.AuthResponse
import com.the3rdwheel.breeze.reddit.RedditUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface Auth {

    @FormUrlEncoded
    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    @POST("access_token")
    suspend fun getAuthResponse(
        @Header("Authorization") credentials: String,
        @Field("grant_type") grant_Type: String = "https://oauth.reddit.com/grants/installed_client",
        @Field("device_id") device_Id: String = "DO_NOT_TRACK_THIS_DEVICE"

    ): AuthResponse


    companion object {


        operator fun invoke(): Auth {

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(RedditUtils.REDDIT_AUTH_URL)
                .addConverterFactory(MoshiConverterFactory.create())


            return retrofitBuilder.build().create(Auth::class.java)
        }

    }
}