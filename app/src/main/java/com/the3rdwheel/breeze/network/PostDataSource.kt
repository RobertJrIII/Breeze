package com.the3rdwheel.breeze.network

import androidx.paging.PageKeyedDataSource
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi

class PostDataSource(val redditApi: RedditApi) : PageKeyedDataSource<String, PostData>() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PostData>
    ) {
        TODO("Not yet implemented")
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {
        TODO("Not yet implemented")
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {
        TODO("Not yet implemented")
    }

}