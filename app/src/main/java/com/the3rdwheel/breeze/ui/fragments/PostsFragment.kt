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

    private lateinit var mAdapter: PostAdapter
    private var _binding: PostsFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PostsFragmentBinding.inflate(inflater, container, false)
        mAdapter = PostAdapter()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val factory = PostViewModel.Factory(get(), "r/news")
        val postViewModel = ViewModelProvider(this, factory).get(PostViewModel::class.java)



        postViewModel.getPosts().observe(viewLifecycleOwner, Observer {
            mAdapter.submitList(it)

        })
        binding.postRecyclerview.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


