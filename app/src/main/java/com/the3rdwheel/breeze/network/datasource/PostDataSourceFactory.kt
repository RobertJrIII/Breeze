package com.the3rdwheel.breeze.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi

class PostDataSourceFactory(private val redditApi: RedditApi) :
    DataSource.Factory<String, PostData>() {
    val sourceLiveData = MutableLiveData<PostDataSource>()

    override fun create(): DataSource<String, PostData> {
        val postDataSource = PostDataSource(redditApi)
        sourceLiveData.postValue(postDataSource)
        return postDataSource
    }

}