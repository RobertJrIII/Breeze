package com.the3rdwheel.breeze.reddit.models.data.children.posts

import com.squareup.moshi.Json
import com.the3rdwheel.breeze.reddit.models.data.children.posts.data.PostData

data class Post(
    val kind: String,
    @Json(name = "data") val postData: PostData
)