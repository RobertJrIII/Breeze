package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.the3rdwheel.breeze.BuildConfig
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.ViewModel
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.authentication.db.entity.Account
import com.the3rdwheel.breeze.reddit.ANONYMOUS_USER
import com.the3rdwheel.breeze.reddit.CREDENTIALS
import kotlinx.android.synthetic.main.posts_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import okio.IOException
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

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


