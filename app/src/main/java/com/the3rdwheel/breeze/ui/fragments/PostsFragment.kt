package com.the3rdwheel.breeze.ui.fragments

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.the3rdwheel.breeze.databinding.PostsFragmentBinding
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
        val prefs = requireContext().getSharedPreferences("prefs", Application.MODE_PRIVATE)
        val ran = prefs.getBoolean("firstSetUp", true)

        if(!ran){
            val redditApi = get<RedditApi>()
            CoroutineScope(IO).launch {

                val t = redditApi.getPosts().data.children[0].data.toString()

                withContext(Main) {
                    binding.postTextView.text = t
                }
            }
        }else{
            Toast.makeText(context,"Token not set", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}


