package com.the3rdwheel.breeze.reddit.models.data.children

import com.squareup.moshi.Json
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData

data class Children(
    val kind: String,
    @Json(name = "data") val postData: PostData
)

