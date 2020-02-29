package com.the3rdwheel.breeze.reddit.models.data

import com.squareup.moshi.Json
import com.the3rdwheel.breeze.reddit.models.data.children.Children

data class Data(
    val modhash: String,
    val dist: Int,
    @Json(name = "children") val children: Children,
    val after: String,
    val before: String?
)