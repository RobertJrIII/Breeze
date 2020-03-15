package com.the3rdwheel.breeze.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.emoji.widget.EmojiTextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData

class PostAdapter : PagedListAdapter<PostData, PostAdapter.PostViewHolder>(getAsyncDifferConfig()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.post_item, parent, false)
        return PostViewHolder(v)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPostData = getItem(position)
        holder.mAuthor.text = currentPostData?.author
        holder.mTitle.text = currentPostData?.title
        holder.mSubReddit.text = currentPostData?.subreddit_name_prefixed
    }

    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTitle: EmojiTextView = itemView.findViewById(R.id.postTitle)
        val mAuthor: EmojiTextView = itemView.findViewById(R.id.postAuthor)
        val mSubReddit: EmojiTextView = itemView.findViewById(R.id.postSubbreddit)

    }


    companion object {
        fun getAsyncDifferConfig(): AsyncDifferConfig<PostData> {

            val diffCallback = object : DiffUtil.ItemCallback<PostData>() {
                override fun areItemsTheSame(oldItem: PostData, newItem: PostData): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: PostData, newItem: PostData): Boolean =
                    oldItem == newItem


            }
            return AsyncDifferConfig.Builder(diffCallback).build()
        }


    }
}