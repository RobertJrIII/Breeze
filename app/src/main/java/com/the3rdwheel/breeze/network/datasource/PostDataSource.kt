package com.the3rdwheel.breeze.network.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.reddit.models.data.Data
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import timber.log.Timber

private const val isInitialLoad: Boolean = true

class PostDataSource(
    private val scope: CoroutineScope,
    private val redditApi: RedditApi,
    private val subName: String?
) :
    PageKeyedDataSource<String, PostData>() {

    private val networkState = MutableLiveData<NetworkState>()
    private var retryQuery: (() -> Unit)? = null
    private val hasPostsLiveDara = MutableLiveData<Boolean>()
    private val initialLoadStateLiveData = MutableLiveData<NetworkState>()
    private var supervisorJob: CompletableJob = SupervisorJob()

    fun getInitialLoadStateData(): LiveData<NetworkState> = initialLoadStateLiveData
    fun getHasPostData(): LiveData<Boolean> = hasPostsLiveDara
    fun getNetworkState(): LiveData<NetworkState> = networkState


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PostData>
    ) {
        retryQuery = { loadInitial(params, callback) }
        execute(isInitialLoad, subName, params.requestedLoadSize) { data ->
            val posts = data.children.map { it.data }

            callback.onResult(posts, data.before, data.after)
        }
//        initialLoadStateLiveData.postValue(NetworkState.LOADING)
//
//        scope.launch(IO) {
//            try {
//                val response = redditApi.getPosts(subName, params.requestedLoadSize)
//                if (response.isSuccessful && response.body() != null) {
//                    val data = response.body()?.data
//                    val redditPosts = data?.children?.map { it.data }
//
//                    initialLoadStateLiveData.postValue(NetworkState.SUCCESS)
//                    retryQuery = null
//
//                    callback.onResult(redditPosts!!, data.before, data.after)
//                }
//            } catch (e: Exception) {
//                Timber.e(e)
//                initialLoadStateLiveData.postValue(NetworkState.FAILED)
//            }
//        }


    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {

        retryQuery = { loadAfter(params, callback) }

        execute(!isInitialLoad, subName, params.requestedLoadSize, params.key) { data ->
            val posts = data.children.map { it.data }
            callback.onResult(posts, data.after)

        }
//        networkState.postValue(NetworkState.LOADING)
//
//        scope.launch(IO) {
//            try {
//                val response = redditApi.getPosts(subName, params.requestedLoadSize, params.key)
//                if (response.isSuccessful && response.body() != null) {
//                    val data = response.body()?.data
//                    val redditPosts = data?.children?.map { it.data }
//
//                    networkState.postValue(NetworkState.SUCCESS)
//                    retryQuery = null
//
//                    callback.onResult(redditPosts!!, data.after)
//                }
//            } catch (e: Exception) {
//                Timber.e(e)
//                networkState.postValue(NetworkState.FAILED)
//            }
//        }


    }

    private fun getJobErrorHandler(isInitial: Boolean) = CoroutineExceptionHandler { _, e ->
        Timber.e(e)
        setNetworkState(isInitial, NetworkState.FAILED)
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {}


    fun retryFailedLoading() {
        val prevQuery = retryQuery
        retryQuery = null
        prevQuery?.invoke()
    }

    private fun setNetworkState(isInitial: Boolean, networkStatus: NetworkState) {
        if (isInitial) {
            initialLoadStateLiveData.postValue(networkStatus)
        } else {
            networkState.postValue(networkStatus)
        }
    }

    private fun execute(
        isInitial: Boolean,
        subName: String?,
        perPage: Int,
        nextKey: String = "",
        callback: (Data) -> Unit
    ) {


        setNetworkState(isInitial, NetworkState.LOADING)
        scope.launch(IO + getJobErrorHandler(isInitial) + supervisorJob) {

            val response = redditApi.getPosts(subName, perPage, nextKey)

            if (response.isSuccessful && response.body() != null) {
                val data = response.body()?.data
                setNetworkState(isInitial, NetworkState.SUCCESS)
                retryQuery = null
                callback(data!!)
            }  //maybe add Failed here as well just in case


        }
    }

    override fun invalidate() {
        super.invalidate()
        supervisorJob.cancelChildren()   // Cancel possible running job to only keep last result searched by user
    }
}