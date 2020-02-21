package com.the3rdwheel.breeze.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import com.isupatches.wisefy.WiseFy
import com.the3rdwheel.breeze.BuildConfig

import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AuthDatabase
import com.the3rdwheel.breeze.authentication.network.AuthDataSource
import com.the3rdwheel.breeze.authentication.network.ConnectivityInterceptor
import com.the3rdwheel.breeze.authentication.repository.AuthRepository
import kotlinx.android.synthetic.main.posts_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okhttp3.Credentials
import timber.log.Timber

class PostsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.posts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val wisefy = WiseFy.Brains(this.context!!).getSmarts()

        postTextView.text = wisefy.isDeviceConnectedToMobileOrWifiNetwork().toString()

        val apiService = Auth(ConnectivityInterceptor(this@PostsFragment.context!!))
//        val authDataSource = AuthDataSource(apiService)
//        authDataSource.downloadedAuthResponse.observe(viewLifecycleOwner, Observer {
//            Toast.makeText(this@PostsFragment.context, it.access_token, Toast.LENGTH_LONG).show()
//            Timber.d("Response ${it.user} ${it.token_type} ${it.access_token}")
//        })

        //val repository = AuthRepository(AuthDatabase.invoke(this), AuthDataSource(apiService))
        CoroutineScope(IO).launch {
            //authDataSource.fetchAuthResponse(Credentials.basic(BuildConfig.CLIENT_ID, ""))

        }
    }
}