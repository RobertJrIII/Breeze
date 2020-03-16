package com.the3rdwheel.breeze.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.CoroutineScope

class PostDataSourceFactory(private val scope: CoroutineScope, private val redditApi: RedditApi) :
    DataSource.Factory<String, PostData>() {
    private val sourceLiveData = MutableLiveData<PostDataSource>()
    private lateinit var postDataSource: PostDataSource
    override fun create(): DataSource<String, PostData> {
        postDataSource = PostDataSource(scope, redditApi)
        sourceLiveData.postValue(postDataSource)
        return postDataSource
    }

    fun getDataSource() = postDataSource
}