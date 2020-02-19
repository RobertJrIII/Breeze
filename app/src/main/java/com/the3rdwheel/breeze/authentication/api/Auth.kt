package com.the3rdwheel.breeze.authentication.api

import com.the3rdwheel.breeze.BuildConfig
import com.the3rdwheel.breeze.authentication.AuthResponse
import com.the3rdwheel.breeze.reddit.REDDIT_AUTH_URL
import okhttp3.Credentials
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface Auth {
    /**
     * @param credentials The client id and password encoded with Base64
     * @param grant_Type default https://oauth.reddit.com/grants/installed_client
     * @param device_Id a random UUID String or leave blank for DO_NOT_TRACK_THIS_DEVICE
     * @return returns the response containing access token and token type as well what the reddit api returns
     */
    @FormUrlEncoded
    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    @POST("access_token")
    suspend fun getAppOnlyOathToken(
        @Header("Authorization") credentials: String,
        @Field("grant_type") grant_Type: String = "https://oauth.reddit.com/grants/installed_client",
        @Field("device_id") device_Id: String = "DO_NOT_TRACK_THIS_DEVICE"

    ): AuthResponse


    companion object {

        operator fun invoke(): Auth {

            val retrofitBuilder = Retrofit.Builder()
                .baseUrl(REDDIT_AUTH_URL)
                .addConverterFactory(MoshiConverterFactory.create())


            return retrofitBuilder.build().create(Auth::class.java)
        }

    }
}