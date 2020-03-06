package com.the3rdwheel.breeze.ui.fragments

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import at.favre.lib.armadillo.Armadillo
import com.the3rdwheel.breeze.databinding.PostsFragmentBinding
import com.the3rdwheel.breeze.reddit.RedditUtils
import com.the3rdwheel.breeze.reddit.retrofit.RedditApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.get


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
        val token = Armadillo.create(
            context?.getSharedPreferences(
                RedditUtils.SECURE_PREFS,
                MODE_PRIVATE
            )
        ).encryptionFingerprint(context).build().getString(RedditUtils.AUTH_KEY, "")

        val redditApi = get<RedditApi>()
        CoroutineScope(IO).launch {

            val t =
                redditApi.getPosts(RedditUtils.getOAuthHeader(token!!)).data.children[0].data.toString()

            withContext(Main) {
                binding.postTextView.text = t
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


