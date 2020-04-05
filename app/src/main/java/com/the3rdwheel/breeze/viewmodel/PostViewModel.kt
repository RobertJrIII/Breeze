package com.the3rdwheel.breeze.viewmodel

import androidx.lifecycle.*
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.network.datasource.PostDataSourceFactory
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.api.RedditApi


class PostViewModel(redditApi: RedditApi, subName: String) : ViewModel() {
    private val postList: LiveData<PagedList<PostData>>


    private val postDataSourceFactory: PostDataSourceFactory =
        PostDataSourceFactory(viewModelScope, redditApi, subName)
    val networkState: LiveData<NetworkState> =
        switchMap(postDataSourceFactory.postSourceLiveData) { it.networkState }

    val hasPostLiveData: LiveData<Boolean> =
        switchMap(postDataSourceFactory.postSourceLiveData) { it.hasPostLiveData }

    val initialLoadData: LiveData<NetworkState> =
        switchMap(postDataSourceFactory.postSourceLiveData) { it.initialLoadStateLiveData }

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(25)
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

    fun retryLoadingPosts() = postDataSourceFactory.getPostDataSource().retryFailedLoading()

    fun refresh() {
        postDataSourceFactory.getPostDataSource().invalidate()
    }
}

