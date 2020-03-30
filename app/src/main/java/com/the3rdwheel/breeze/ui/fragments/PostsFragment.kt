package com.the3rdwheel.breeze.ui.fragments

import android.content.res.Resources
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.adapters.PostAdapter
import com.the3rdwheel.breeze.databinding.PostsFragmentBinding
import com.the3rdwheel.breeze.gestures.*
import com.the3rdwheel.breeze.network.NetworkAssistance
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.viewmodel.CommunicationViewModel
import com.the3rdwheel.breeze.viewmodel.PostViewModel
import org.koin.android.ext.android.get

const val topPosition = 0

class PostsFragment : Fragment(), NetworkAssistance {
    private var hasPost = false
    private var _binding: PostsFragmentBinding? = null
    private val binding get() = _binding!!

    private val postViewModel: PostViewModel by viewModels { PostViewModel.Factory(get()) }
    private val mAdapter = PostAdapter(this) //try to save instance
    private val sharedViewModel: CommunicationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostsFragmentBinding.inflate(inflater, container, false)


        binding.postSwipeRefresh.setOnRefreshListener(this::refresh)



        binding.postRecyclerview.apply {
            adapter = mAdapter
            //addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }


        //    observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        sharedViewModel.getPostFragmentReselected().observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.postRecyclerview.scrollToPosition(topPosition)
            }
        })
    }


    private fun observeViewModel() {

        postViewModel.getPosts().observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)

        })

        postViewModel.initialLoadData.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.SUCCESS -> {
                    binding.postSwipeRefresh.isRefreshing = false
                    binding.redditNotAvailable.visibility =
                        View.GONE //added don't know if adding this will remove has posts
                }
                NetworkState.FAILED -> {
                    binding.postSwipeRefresh.isRefreshing = false
                    binding.redditNotAvailable.setOnClickListener { refresh() }
                    showError()
                }
                else -> {
                    binding.postSwipeRefresh.isRefreshing = true
                    binding.redditNotAvailable.visibility =
                        View.GONE //added don't know if adding this will remove has posts
                }
            }
        })


//        postViewModel.hasPostLiveData.observe(viewLifecycleOwner, Observer {
//            this.hasPost = it
//            binding.postSwipeRefresh.isRefreshing = false
//            if (it) {
//                binding.redditNotAvailable.visibility = View.GONE
//            } else {
//                binding.redditNotAvailable.setOnClickListener { }
//                // showError() idk maybe no needed
//            }
//        })

        postViewModel.networkState.observe(viewLifecycleOwner, Observer {
            mAdapter.updateNetworkState(it)
        })
    }

    private fun showError() {
        binding.postSwipeRefresh.isRefreshing = false
        binding.redditNotAvailable.visibility = View.VISIBLE
    }


    private fun refresh() {
        mAdapter.removeFooter()
        binding.redditNotAvailable.visibility = View.GONE
        hasPost = false
        postViewModel.refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun retryLoadingMore() {
        postViewModel.retryLoadingPosts()
    }

//    private fun getColor(resources: Resources, colorID: Int, theme: Resources.Theme? = null) =
//        ResourcesCompat.getColor(resources, colorID, theme)
//
//
//    SimpleSwipe(
//    LEFT_AND_RIGHT,
//    listOf(
//    SwipeItems(
//    getColor(resources, R.color.save),
//    R.drawable.save_24dp
//    ),
//    SwipeItems(
//    getColor(resources, R.color.moreOptions),
//    R.drawable.more_24dp
//    ),
//    SwipeItems(
//    getColor(resources, R.color.downVoteColor),
//    R.drawable.down_vote_24dp
//    ),
//    SwipeItems(
//    getColor(resources, R.color.upVoteColor),
//    R.drawable.up_vote_24dp
//    )
//    ),
//    binding.postRecyclerview
//    )
}


