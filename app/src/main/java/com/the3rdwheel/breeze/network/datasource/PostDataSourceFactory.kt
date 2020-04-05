package com.the3rdwheel.breeze.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.api.RedditApi
import kotlinx.coroutines.CoroutineScope

class PostDataSourceFactory(
    private val scope: CoroutineScope,
    private val redditApi: RedditApi,
    private val subName: String?
) :
    DataSource.Factory<String, PostData>() {
    private val postSourceLiveData = MutableLiveData<PostDataSource>()
    private lateinit var postDataSource: PostDataSource


    override fun create(): DataSource<String, PostData> {
        postDataSource = PostDataSource(scope, redditApi, subName)
        postSourceLiveData.postValue(postDataSource)
        return postDataSource
    }

    fun getPostDataSourceLiveData() = postSourceLiveData

    fun getPostDataSource(): PostDataSource {
        return postDataSource
    }
}