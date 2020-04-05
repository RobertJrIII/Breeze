package com.the3rdwheel.breeze.reddit.api

import com.the3rdwheel.breeze.network.BaseHeader
import com.the3rdwheel.breeze.network.RedditAuthenticator
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.models.Submission
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RedditApi {

    @GET("search")
    suspend fun getSearchResults(@Query("q") query: String): Submission


    @GET("{subName}/")
    suspend fun getPosts(
        @Path("subName") subName: String? = "",
        @Query("limit") loadSize: Int = 25,
        @Query("after") after: String? = ""
    ): retrofit2.Response<Submission>


    companion object {
        operator fun invoke(
            redditAuthenticator: RedditAuthenticator,
            baseHeader: BaseHeader
        ): RedditApi {

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(baseHeader)
                .authenticator(redditAuthenticator)
                .addInterceptor(redditAuthenticator)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

            val retrofitBuilder = Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(RedditUtils.REDDIT_BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())


            return retrofitBuilder.build().create(RedditApi::class.java)

        }

    }

}