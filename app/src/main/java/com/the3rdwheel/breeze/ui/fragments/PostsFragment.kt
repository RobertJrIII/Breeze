package com.the3rdwheel.breeze.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.isupatches.wisefy.WiseFy
import com.the3rdwheel.breeze.BuildConfig

import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.authentication.db.entity.Account
import com.the3rdwheel.breeze.authentication.network.ConnectivityInterceptor
import kotlinx.android.synthetic.main.posts_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import okio.IOException

class PostsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.posts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val wisefy = WiseFy.Brains(this.context!!).getSmarts()

        postTextView.text = wisefy.isDeviceConnectedToMobileOrWifiNetwork().toString()

        val apiService = Auth(ConnectivityInterceptor(this@PostsFragment.context!!))
//        val authDataSource = AuthDataSource(apiService)
//        authDataSource.downloadedAuthResponse.observe(viewLifecycleOwner, Observer {
//
//            Toast.makeText(this@PostsFragment.context, it.access_token, Toast.LENGTH_LONG).show()
//            //Timber.d("Response  ${it.token_type} ${it.access_token}")
//        })


        CoroutineScope(IO).launch {
            val database = AccountDatabase.invoke(context!!)
            try {

                val response = apiService.getAuthResponse(Credentials.basic(BuildConfig.CLIENT_ID, ""))



                database.accountDao().insert(Account("Anonymous", 0, response, 1))
                // val text = database.accountDao().getUser("Anonymous").authResponse.access_token


            } catch (e: IOException) {
                val text = database.accountDao().getUser("Anonymous").authResponse.access_token
                withContext(Main) {
                    postTextView.text = text

                }

            }
        }
    }
}