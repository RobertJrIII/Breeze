package com.the3rdwheel.breeze.items

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.databinding.PostItemBinding
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData

class PostItem(private val data: PostData) : AbstractBindingItem<PostItemBinding>() {


    override fun bindView(binding: PostItemBinding, payloads: List<Any>) {
        binding.postAuthor.text = data.author
        binding.postTitle.text = data.title
        binding.postSubbreddit.text = data.subreddit_name_prefixed
    }

    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup?): PostItemBinding {
        return PostItemBinding.inflate(inflater, parent, false)
    }

    override val type: Int
        get() = R.id.postItem
}