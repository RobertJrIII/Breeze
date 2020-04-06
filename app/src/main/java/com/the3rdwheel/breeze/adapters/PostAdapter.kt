package com.the3rdwheel.breeze.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.forEach
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the3rdwheel.breeze.databinding.ErrorPostRetryBinding
import com.the3rdwheel.breeze.databinding.LoadingBinding
import com.the3rdwheel.breeze.databinding.PostItemBinding
import com.the3rdwheel.breeze.network.NetworkAssistance
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.ui.viewholders.LoadingViewHolder
import com.the3rdwheel.breeze.ui.viewholders.PostErrorViewHolder
import com.the3rdwheel.breeze.ui.viewholders.PostViewHolder
import timber.log.Timber

private const val LOADING_LAYOUT = 0
private const val ERROR_LAYOUT = 1
private const val POST_LAYOUT = 2


class PostAdapter(
    private val networkAssistance: NetworkAssistance,
    context: Context
) :
    PagedListAdapter<PostData, RecyclerView.ViewHolder>(getAsyncDifferConfig()) {
    private val glide = Glide.with(context)
    private var networkState: NetworkState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            POST_LAYOUT -> PostViewHolder(
                PostItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            LOADING_LAYOUT -> LoadingViewHolder(
                LoadingBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            ERROR_LAYOUT -> PostErrorViewHolder(
                ErrorPostRetryBinding.inflate(
                    layoutInflater, parent, false
                )
            )
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is PostViewHolder -> {
                val postData = getItem(position)
                Timber.v(postData.toString())
                holder.mAuthor.text = postData!!.author
                holder.mTitle.text = postData.title
                holder.mSubReddit.text = postData.subreddit_name_prefixed

                postData.all_awardings!!.forEach {

                    val awardImage = ImageView(holder.itemView.context).apply {
                        maxHeight = 24
                        maxWidth = 24
                    }


                    glide.load(it.icon_url).override(24).into(awardImage)
                    holder.mAwards.addView(awardImage)
                }


            }
            is LoadingViewHolder -> {
                holder.postLoading.isIndeterminate = true
            }
            is PostErrorViewHolder -> {
                holder.retryButton.setOnClickListener {
                    networkAssistance.retryLoadingMore()
                }
            }
            else -> {
                throw IllegalArgumentException("Unknown holder")
            }
        }

    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        if (holder is PostViewHolder) {
            // if (holder.mAwards.childCount > 0) {
            holder.mAwards.forEach {
                glide.clear(it)
            }

        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            if (networkState == NetworkState.LOADING) {
                LOADING_LAYOUT
            } else {
                ERROR_LAYOUT
            }
        } else {
            POST_LAYOUT
        }
    }

    override fun getItemCount(): Int {
        return if (hasExtraRow()) {
            super.getItemCount() + 1
        } else {
            super.getItemCount()
        }
    }

    private fun hasExtraRow() =
        networkState != null && networkState != NetworkState.SUCCESS   // add extra row when loading or error

    fun removeFooter() {
        if (hasExtraRow()) {
            notifyItemRemoved(itemCount - 1)
        }
        networkState = null
    }

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