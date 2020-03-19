package com.the3rdwheel.breeze.network.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.*
import timber.log.Timber

class PostDataSource(
    private val scope: CoroutineScope,
    private val redditApi: RedditApi,
    private val subName: String?
) :
    PageKeyedDataSource<String, PostData>() {
    private val networkState = MutableLiveData<NetworkState>()


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PostData>
    ) {
        networkState.postValue(NetworkState.LOADING)

        scope.launch {


            try {
                val response = redditApi.getPosts(subName, params.requestedLoadSize)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data
                    val redditPosts = data?.children?.map { it.data }
                    networkState.postValue(NetworkState.SUCCESS)

                    callback.onResult(redditPosts!!, data.before, data.after)
                }

            } catch (e: Exception) {
                Timber.e(e)
                networkState.postValue(NetworkState.FAILED)
            }
        }


    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {
        networkState.postValue(NetworkState.LOADING)

        scope.launch {

            try {
                val response = redditApi.getPosts(subName, params.requestedLoadSize, params.key)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data
                    networkState.postValue(NetworkState.SUCCESS)
                    val redditPosts = data?.children?.map { it.data }

                    callback.onResult(redditPosts!!, data.after)
                }
            } catch (e: Exception) {
                Timber.e(e)
                networkState.postValue(NetworkState.FAILED)
            }
        }

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {

//        scope.launch {
//
//            try {
//                val response =
//                    redditApi.getPosts(subName, params.requestedLoadSize, before = params.key)
//
//                if (response.isSuccessful && response.body() != null) {
//                    val data = response.body()?.data
//                    val redditPosts = data?.children?.map { it.data }
//                    callback.onResult(redditPosts!!, data.after)
//
//                }
//
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//
//        }
    }


}