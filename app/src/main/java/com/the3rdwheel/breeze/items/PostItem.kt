package com.the3rdwheel.breeze.items

import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.post_item.*

class PostItem(private val data: PostData) : Item() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.postTitle.text = data.title
        viewHolder.postTitle.text = data.title
        viewHolder.postSubbreddit.text = data.subreddit_name_prefixed
    }

    override fun getLayout() = R.layout.post_item


    companion object {
        fun getAsyncDifferConfig(): AsyncDifferConfig<PostData> {

            val DIFF_UTIL = object : DiffUtil.ItemCallback<PostData>() {
                override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean =
                    oldItem == newItem


            }
            return AsyncDifferConfig.Builder(DIFF_UTIL).build()
        }


    }
}