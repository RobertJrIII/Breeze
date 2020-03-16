package com.the3rdwheel.breeze.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.CoroutineScope

class PostDataSourceFactory(
    private val scope: CoroutineScope,
    private val redditApi: RedditApi,
    private val subName: String? = ""
) :
    DataSource.Factory<String, PostData>() {
    private val sourceLiveData = MutableLiveData<PostDataSource>()
    override fun create(): DataSource<String, PostData> {
        val postDataSource = PostDataSource(scope, redditApi, subName)
        sourceLiveData.postValue(postDataSource)
        return postDataSource
    }

}