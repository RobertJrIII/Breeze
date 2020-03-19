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
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import me.zhanghai.android.materialprogressbar.MaterialProgressBar


class PostAdapter : PagedListAdapter<PostData, RecyclerView.ViewHolder>(getAsyncDifferConfig()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.post_item -> PostViewHolder(view)
            R.layout.loading -> LoadingViewHolder(view)
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PostViewHolder) {
            val currentPostData = getItem(position)
            holder.mAuthor.text = currentPostData?.author
            holder.mTitle.text = currentPostData?.title
            holder.mSubReddit.text = currentPostData?.subreddit_name_prefixed
        } else {
            (holder as LoadingViewHolder).postLoading
        }

    }


    private var networkState: NetworkState? = null

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.loading
        } else {
            R.layout.post_item
        }
    }

    override fun getItemCount(): Int {
        return if (hasExtraRow()) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.SUCCESS


    fun updateNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }


    class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mTitle: EmojiTextView = itemView.findViewById(R.id.postTitle)
        val mAuthor: EmojiTextView = itemView.findViewById(R.id.postAuthor)
        val mSubReddit: EmojiTextView = itemView.findViewById(R.id.postSubbreddit)

    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val postLoading: MaterialProgressBar = itemView.findViewById(R.id.postLoading)
    }

    companion object {
        private fun getAsyncDifferConfig(): AsyncDifferConfig<PostData> {

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