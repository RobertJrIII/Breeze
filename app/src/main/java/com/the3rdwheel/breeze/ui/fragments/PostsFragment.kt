package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.ViewModel
import kotlinx.android.synthetic.main.posts_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.posts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModel: ViewModel by viewModel()
        viewModel.setUser()


        viewModel.getAccessToken().let {
            it?.observe(viewLifecycleOwner, Observer {

                postTextView.text = it.authResponse.access_token
            })
        }


    }
}


