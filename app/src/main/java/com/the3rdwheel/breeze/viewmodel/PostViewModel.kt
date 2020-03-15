package com.the3rdwheel.breeze.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.the3rdwheel.breeze.network.datasource.PostDataSource
import com.the3rdwheel.breeze.network.datasource.PostDataSourceFactory
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi


class PostViewModel(redditApi: RedditApi) : ViewModel() {
    private val postList: LiveData<PagedList<PostData>>

    private val postDataSourceFactory: PostDataSourceFactory =
        PostDataSourceFactory(viewModelScope, redditApi)

    init {

        val config = PagedList.Config.Builder()
            .setPageSize(35)
            .setInitialLoadSizeHint(35)
            .setEnablePlaceholders(true)
            .build()

        postList = LivePagedListBuilder(postDataSourceFactory, config).build()
    }


    fun getPosts() = postList
}