package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.the3rdwheel.breeze.adapters.PostAdapter
import com.the3rdwheel.breeze.databinding.PostsFragmentBinding
import com.the3rdwheel.breeze.viewmodel.PostViewModel
import org.koin.android.ext.android.get


class PostsFragment : Fragment() {
    private var hasPost = false
    private var _binding: PostsFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = PostViewModel.Factory(get())
        val postViewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)

        val mAdapter = PostAdapter()
        binding.postRecyclerview.adapter = mAdapter


        postViewModel.getPosts().observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)

        })

//        postViewModel.hasPostLiveData.observe(viewLifecycleOwner, Observer {
//            this.hasPost = it
//            binding.postSwipeRefresh.isRefreshing = false
//        })

//TODO create error background


        postViewModel.networkState?.observe(viewLifecycleOwner, Observer {
            mAdapter.updateNetworkState(it)
        })


        binding.postSwipeRefresh.setOnRefreshListener {
            hasPost = false
            postViewModel.refresh()
            binding.postSwipeRefresh.isRefreshing = false

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


