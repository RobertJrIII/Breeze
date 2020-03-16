package com.the3rdwheel.breeze.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.the3rdwheel.breeze.network.datasource.PostDataSourceFactory
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi


class PostViewModel(redditApi: RedditApi) : ViewModel() {
    private val postList: LiveData<PagedList<PostData>>
    var subName: String? = ""

    private val postDataSourceFactory: PostDataSourceFactory =
        PostDataSourceFactory(viewModelScope, redditApi, subName)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(35)
            .setInitialLoadSizeHint(35)
            .setEnablePlaceholders(false)
            .build()

        postList = LivePagedListBuilder(postDataSourceFactory, config).build()
    }


    fun getPosts() = postList
}