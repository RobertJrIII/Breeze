package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.the3rdwheel.breeze.databinding.PostsFragmentBinding
import com.the3rdwheel.breeze.items.PostItem
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import timber.log.Timber


class PostsFragment : Fragment() {
    private lateinit var groupAdapter: GroupAdapter<GroupieViewHolder>
    private var _binding: PostsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        groupAdapter = GroupAdapter()
        _binding = PostsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.postRecyclerview.adapter = groupAdapter

        val redditApi = get<RedditApi>()

        CoroutineScope(IO).launch {

            try {
                val response =
                    redditApi.getPosts("", "").body()?.data?.children

                if (response != null) {
                    for (post in response) {
                        withContext(Main) {
                            groupAdapter.add(
                                PostItem(
                                    post.data
                                )
                            )
                        }
                    }
                }


            } catch (e: Exception) {
                Timber.e(e)
            }


        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


