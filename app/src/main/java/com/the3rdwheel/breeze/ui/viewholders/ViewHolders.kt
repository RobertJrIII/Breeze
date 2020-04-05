package com.the3rdwheel.breeze.ui.viewholders

import android.widget.Button
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.nex3z.flowlayout.FlowLayout
import com.the3rdwheel.breeze.databinding.ErrorPostRetryBinding
import com.the3rdwheel.breeze.databinding.LoadingBinding
import com.the3rdwheel.breeze.databinding.PostItemBinding
import com.the3rdwheel.breeze.network.NetworkAssistance
import com.the3rdwheel.breeze.reddit.models.data.children.postdata.PostData
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class PostViewHolder(postItemBinding: PostItemBinding) :
    RecyclerView.ViewHolder(postItemBinding.root) {


    private val mTitle: EmojiTextView = postItemBinding.postTitle
    private val mAuthor: EmojiTextView = postItemBinding.postAuthor
    private val mSubReddit: EmojiTextView = postItemBinding.postSubbreddit
    private val mAwards: FlowLayout = postItemBinding.awards


    fun bind(postData: PostData) {
        mAuthor.text = postData.author
        mTitle.text = postData.title
        mSubReddit.text = postData.subreddit_name_prefixed
    }

}

class LoadingViewHolder(loadingBinding: LoadingBinding) :
    RecyclerView.ViewHolder(loadingBinding.root) {
    private val postLoading: MaterialProgressBar = loadingBinding.postLoading

    fun bind(isIndeterminate: Boolean) {
        postLoading.isIndeterminate = isIndeterminate
    }
}


class PostErrorViewHolder(errorPostRetryBinding: ErrorPostRetryBinding) :
    RecyclerView.ViewHolder(errorPostRetryBinding.root) {
    private val retryButton: Button = errorPostRetryBinding.footerErrorButton

    fun bind(networkAssistance: NetworkAssistance) {
        retryButton.setOnClickListener {
            networkAssistance.retryLoadingMore()
        }
    }
    //TODO maybe add an interface method for bind repeated code

}