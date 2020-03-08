package com.the3rdwheel.breeze.reddit.retrofit

import com.the3rdwheel.breeze.network.SupportInterceptor
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.models.Submission
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface RedditApi {


    @GET("search")
    suspend fun getSearchResults(@Query("q") query: String): Submission


    @GET(".json")
    suspend fun getPosts(): Submission


    companion object {
        operator fun invoke(
            supportInterceptor: SupportInterceptor
        ): RedditApi {

            val okHttpClient = OkHttpClient.Builder()
                .authenticator(supportInterceptor)
                .addInterceptor(supportInterceptor)
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