package com.the3rdwheel.breeze.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.the3rdwheel.breeze.network.datasource.PostDataSourceFactory
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi


class PostViewModel(redditApi: RedditApi) : ViewModel() {
    private val postList: LiveData<PagedList<PostData>>
    private val postDataSourceFactory: PostDataSourceFactory = PostDataSourceFactory(redditApi)

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(25)
            .setInitialLoadSizeHint(25)
            .setEnablePlaceholders(true)
            .build()

        postList = LivePagedListBuilder(postDataSourceFactory, config).build()
    }


}