package com.the3rdwheel.breeze.ui.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.the3rdwheel.breeze.databinding.PostsFragmentBinding
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okio.IOException
import org.koin.android.ext.android.get
import timber.log.Timber


class PostsFragment : Fragment() {

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


        val redditApi = get<RedditApi>()


        CoroutineScope(IO).launch {
            if (!requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE)
                    .getBoolean("firstSetUp", true)
            ) {

                try {
                    val response =
                        redditApi.getPosts().data.children.toString()

                    withContext(Main) {
                        binding.postTextView.text = response
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }

            }

        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


