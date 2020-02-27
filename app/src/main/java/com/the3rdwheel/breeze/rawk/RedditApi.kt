package com.the3rdwheel.breeze.rawk

import retrofit2.http.GET
import retrofit2.http.Query

interface RedditApi {


    @GET("search")
    suspend fun getSearchResults(@Query("q") query: String)

}