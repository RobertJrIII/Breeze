package com.the3rdwheel.breeze.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.databinding.ErrorPostRetryBinding
import com.the3rdwheel.breeze.databinding.LoadingBinding
import com.the3rdwheel.breeze.databinding.PostItemBinding
import com.the3rdwheel.breeze.network.NetworkAssistance
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import com.the3rdwheel.breeze.ui.viewholders.LoadingViewHolder
import com.the3rdwheel.breeze.ui.viewholders.PostErrorViewHolder
import com.the3rdwheel.breeze.ui.viewholders.PostViewHolder


class PostAdapter(
    private val networkAssistance: NetworkAssistance
) :
    PagedListAdapter<PostData, RecyclerView.ViewHolder>(getAsyncDifferConfig()) {

    private var networkState: NetworkState? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.post_item -> PostViewHolder(
                PostItemBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            R.layout.loading -> LoadingViewHolder(
                LoadingBinding.inflate(
                    layoutInflater,
                    parent,
                    false
                )
            )
            R.layout.error_post_retry -> PostErrorViewHolder(
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
                val currentPostData: PostData? = getItem(position)

                holder.bind(currentPostData!!)
//                val awards = currentPostData.all_awardings
//
//                if (!awards.isNullOrEmpty()) {
//
//                    for (award in awards) {
//
//                        holder.mAwards.addView(ImageView(holder.itemView.context!!).apply {
//                            maxWidth = 10
//                            maxHeight = 10
//                            Glide.with(holder.itemView.context).load(award.icon_url)
//                                .override(24)
//                                .into(this)
//                        })
//
//
//                    }
//
//
//                }
            }
            is LoadingViewHolder -> {
                holder.bind(true)
            }
            is PostErrorViewHolder -> {
                holder.bind(networkAssistance)
            }
            else -> {
                throw IllegalArgumentException("Unknown holder")
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            if (networkState == NetworkState.LOADING) {
                R.layout.loading
            } else {
                R.layout.error_post_retry
            }
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