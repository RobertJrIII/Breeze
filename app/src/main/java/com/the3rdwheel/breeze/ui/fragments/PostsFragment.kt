package com.the3rdwheel.breeze.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.isupatches.wisefy.WiseFy
import com.the3rdwheel.breeze.BuildConfig

import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.authentication.api.Auth
import kotlinx.android.synthetic.main.posts_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials

/**
 * A simple [Fragment] subclass.
 */
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
        CoroutineScope(IO).launch {
            val token = Auth().getAppOnlyOathToken(Credentials.basic(BuildConfig.CLIENT_ID, ""))

            withContext(Main) {
                // postTextView.text = token.access_token
                Toast.makeText(this@PostsFragment.context, token.access_token, Toast.LENGTH_LONG).show()
            }
        }
    }
}