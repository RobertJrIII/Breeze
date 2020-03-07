package com.the3rdwheel.breeze.reddit.models.data


import com.the3rdwheel.breeze.reddit.models.data.children.Children

data class Data(
    val modhash: String?,
    val dist: Int,
    val children: List<Children>,
    val after: String,
    val before: String?
)