package com.the3rdwheel.breeze.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.network.datasource.PostDataSourceFactory
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi


class PostViewModel(redditApi: RedditApi, subName: String) : ViewModel() {
    private val postList: LiveData<PagedList<PostData>>


    private val postDataSourceFactory: PostDataSourceFactory =
        PostDataSourceFactory(viewModelScope, redditApi, subName)
     val networkState: LiveData<NetworkState>? =
        switchMap(postDataSourceFactory.getPostDataSourceData()) { it.getNetworkState() }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(35)
            .setInitialLoadSizeHint(35)
            .setEnablePlaceholders(false)
            .build()

        postList = LivePagedListBuilder(postDataSourceFactory, config).build()


    }


    fun getPosts() = postList


    class Factory(
        private val redditApi: RedditApi,
        private var subName: String? = ""
    ) :
        ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(RedditApi::class.java, String::class.java)
                .newInstance(redditApi, subName)
        }

    }

}

