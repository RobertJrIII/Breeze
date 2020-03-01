package com.the3rdwheel.breeze.reddit.retrofit

import com.the3rdwheel.breeze.network.AccessTokenAuthenticator
import com.the3rdwheel.breeze.network.ConnectivityInterceptor
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.models.Submission
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface RedditApi {


    @GET("search")
    suspend fun getSearchResults(@Query("q") query: String): Submission


    //    @GET("/api/trending_subreddits")
//    suspend fun getTrendingSubs()
    @Headers("Content-Type: application/json")
    @GET("/")
    suspend fun getPosts(): Submission


    companion object {
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor,
            accessTokenAuthenticator: AccessTokenAuthenticator
        ): RedditApi {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor)
                .authenticator(accessTokenAuthenticator)
                .build()

            val retrofitBuilder = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(RedditUtils.REDDIT_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())


            return retrofitBuilder.build().create(RedditApi::class.java)

        }

    }

}