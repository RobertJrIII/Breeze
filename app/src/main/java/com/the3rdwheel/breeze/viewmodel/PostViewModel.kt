package com.the3rdwheel.breeze.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.the3rdwheel.breeze.network.datasource.PostDataSourceFactory
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi


class PostViewModel(private val redditApi: RedditApi) : ViewModel() {
    val postList: LiveData<PagedList<PostData>>

    init {

        val postDataSourceFactory = PostDataSourceFactory(redditApi)
        val config = PagedList.Config.Builder()
            .setPageSize(26)
            .setInitialLoadSizeHint(26)
            .setEnablePlaceholders(false)
            .build()

        postList = LivePagedListBuilder(postDataSourceFactory, config).build()
    }


}