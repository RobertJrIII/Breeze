package com.the3rdwheel.breeze.network.datasource

import androidx.paging.PageKeyedDataSource
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class PostDataSource(val redditApi: RedditApi) : PageKeyedDataSource<String, PostData>() {
    override fun loadInitial(
        params: LoadInitialParams<String>,
        callback: LoadInitialCallback<String, PostData>
    ) {

        CoroutineScope(IO).launch {
            val response = redditApi.getPosts()

            if (response.isSuccessful && response.body() != null) {
                val data = response.body()?.data
                val childrenList = data?.children

                val posts = ArrayList<PostData>()
                for (child in childrenList!!) {
                    posts.add(child.data)
                }
                callback.onResult(posts, data.before, data.after)
            }
        }

    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {
        CoroutineScope(IO).launch {
            val response = redditApi.getPosts(after = params.key)

            if (response.isSuccessful && response.body() != null) {
                val data = response.body()?.data
                val childrenList = data?.children

                val posts = ArrayList<PostData>()
                for (child in childrenList!!) {
                    posts.add(child.data)
                }
                callback.onResult(posts, data.after)
            }
        }

    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<String, PostData>) {

    }

}