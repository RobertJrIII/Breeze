package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.the3rdwheel.breeze.databinding.PostsFragmentBinding
import com.the3rdwheel.breeze.items.PostItem
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get
import timber.log.Timber


class PostsFragment : Fragment() {
//    private lateinit var fastAdapter: FastAdapter<PostItem>
//    private lateinit var itemAdapter: ItemAdapter<PostItem>
    private var _binding: PostsFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = PostsFragmentBinding.inflate(inflater, container, false)
//        itemAdapter = ItemAdapter()
//        fastAdapter = FastAdapter.with(itemAdapter)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


//        binding.postRecyclerview.adapter = fastAdapter
//        val redditApi = get<RedditApi>()
//
//
//        CoroutineScope(IO).launch {
//
//            try {
//                val response =
//                    redditApi.getPosts().data.children
//
//                for (post in response) {
//                    withContext(Main) {
//                        itemAdapter.add(
//                            PostItem(
//                                post.data
//                            )
//                        )
//                    }
//                }
//
//
//            } catch (e: Exception) {
//                Timber.e(e)
//            }
//
//
//        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


