package com.the3rdwheel.breeze.reddit

import com.the3rdwheel.breeze.network.ConnectivityInterceptor
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {


    @GET("search")
    suspend fun getSearchResults(@Query("q") query: String)


    companion object {
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor) {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(connectivityInterceptor).build()
        }

    }

}