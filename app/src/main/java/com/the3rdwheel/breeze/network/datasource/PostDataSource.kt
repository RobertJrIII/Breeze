package com.the3rdwheel.breeze.network.datasource

import androidx.paging.PageKeyedDataSource
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import timber.log.Timber

class PostDataSource(private val scope: CoroutineScope, val redditApi: RedditApi) :
    PageKeyedDataSource<String, PostData>() {

    private var subName: String? = ""


    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PostData>
    ) {

        scope.launch {


            try {
                val response = redditApi.getPosts(subName, params.requestedLoadSize)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data
                    val redditPosts = data?.children?.map { it.data }

                    callback.onResult(redditPosts ?: listOf(), data?.before, data?.after)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }


    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {
        scope.launch {

            try {
                val response = redditApi.getPosts(subName, params.requestedLoadSize, params.key)
                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data

                    val redditPosts = data?.children?.map { it.data }

                    callback.onResult(redditPosts ?: listOf(), data?.after)
                }
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {

        scope.launch {

            try {
                val response =
                    redditApi.getPosts(subName, params.requestedLoadSize, before = params.key)

                if (response.isSuccessful && response.body() != null) {
                    val data = response.body()?.data
                    val redditPosts = data?.children?.map { it.data }
                    callback.onResult(redditPosts ?: listOf(), data?.after)

                }

            } catch (e: Exception) {
                Timber.e(e)
            }

        }


    }

    override fun invalidate() {
        super.invalidate()
        scope.cancel()
    }
}