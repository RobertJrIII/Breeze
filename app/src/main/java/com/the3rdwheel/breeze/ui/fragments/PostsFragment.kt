package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.the3rdwheel.breeze.adapters.PostAdapter
import com.the3rdwheel.breeze.databinding.PostsFragmentBinding
import com.the3rdwheel.breeze.network.Callback
import com.the3rdwheel.breeze.network.NetworkState
import com.the3rdwheel.breeze.viewmodel.PostViewModel
import org.koin.android.ext.android.get


class PostsFragment : Fragment() {
    private var hasPost = false
    private var _binding: PostsFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var mAdapter: PostAdapter

    private val postViewModel: PostViewModel by viewModels { PostViewModel.Factory(get()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostsFragmentBinding.inflate(inflater, container, false)

        binding.postSwipeRefresh.setOnRefreshListener(this::refresh)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        mAdapter = PostAdapter(object : Callback {
            override fun retryLoadingMore() {
                postViewModel.retryLoadingPosts()
            }
        })
        binding.postRecyclerview.adapter = mAdapter



        observeViewModel()

    }

    private fun observeViewModel() {

        postViewModel.initialLoadData.observe(viewLifecycleOwner, Observer {
            when (it) {
                NetworkState.SUCCESS -> {
                    binding.postSwipeRefresh.isRefreshing = false
                }
                NetworkState.FAILED -> {
                    binding.postSwipeRefresh.isRefreshing = false
                    binding.redditNotAvailable.setOnClickListener { refresh() }
                    showError()
                }
                else -> {
                    binding.postSwipeRefresh.isRefreshing = true
                }
            }
        })

        postViewModel.getPosts().observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)

        })

        postViewModel.hasPostLiveData.observe(viewLifecycleOwner, Observer {
            this.hasPost = it
            binding.postSwipeRefresh.isRefreshing = false
            if (it) {
                binding.redditNotAvailable.visibility = View.GONE
            } else {
                binding.redditNotAvailable.setOnClickListener { }
                // showError() idk maybe no needed
            }
        })




        postViewModel.networkState?.observe(viewLifecycleOwner, Observer {
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

}


