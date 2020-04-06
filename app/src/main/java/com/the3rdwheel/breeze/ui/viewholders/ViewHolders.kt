package com.the3rdwheel.breeze.ui.viewholders

import android.widget.Button
import androidx.emoji.widget.EmojiTextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexboxLayout
import com.the3rdwheel.breeze.databinding.ErrorPostRetryBinding
import com.the3rdwheel.breeze.databinding.LoadingBinding
import com.the3rdwheel.breeze.databinding.PostItemBinding
import me.zhanghai.android.materialprogressbar.MaterialProgressBar

class PostViewHolder(postItemBinding: PostItemBinding) :
    RecyclerView.ViewHolder(postItemBinding.root) {


    val mTitle: EmojiTextView = postItemBinding.postTitle
    val mAuthor: EmojiTextView = postItemBinding.postAuthor
    val mSubReddit: EmojiTextView = postItemBinding.postSubbreddit
    val mAwards: FlexboxLayout = postItemBinding.awards


}

class LoadingViewHolder(loadingBinding: LoadingBinding) :
    RecyclerView.ViewHolder(loadingBinding.root) {
    val postLoading: MaterialProgressBar = loadingBinding.postLoading

}


class PostErrorViewHolder(errorPostRetryBinding: ErrorPostRetryBinding) :
    RecyclerView.ViewHolder(errorPostRetryBinding.root) {
    val retryButton: Button = errorPostRetryBinding.footerErrorButton


}