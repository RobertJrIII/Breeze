package com.the3rdwheel.breeze.rawk.authentication.api

import com.the3rdwheel.breeze.rawk.authentication.network.response.AuthResponse
import com.the3rdwheel.breeze.rawk.RedditUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

interface Auth {
    /** Used for application only OAuth
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
    suspend fun getAuthResponse(
        @Header("Authorization") credentials: String,
        @Field("grant_type") grant_Type: String = "https://oauth.reddit.com/grants/installed_client",
        @Field("device_id") device_Id: String = "DO_NOT_TRACK_THIS_DEVICE"

    ): AuthResponse


//    @POST("access_token")
//    fun refreshToken(@Header("Authorization") credentials: String)

    companion object {

        operator fun invoke(): Auth {
            val okHttpClient = OkHttpClient.Builder().build()
            val retrofitBuilder = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(RedditUtils.REDDIT_AUTH_URL)
                .addConverterFactory(MoshiConverterFactory.create())


            return retrofitBuilder.build().create(Auth::class.java)
        }
//        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): Auth {
//            val okHttpClient = OkHttpClient.Builder()
//                .addInterceptor(connectivityInterceptor).build()
//            val retrofitBuilder = Retrofit.Builder()
//                .client(okHttpClient)
//                .baseUrl(RedditUtils.REDDIT_AUTH_URL)
//                .addConverterFactory(MoshiConverterFactory.create())
//
//
//            return retrofitBuilder.build().create(Auth::class.java)
//        }

    }
}