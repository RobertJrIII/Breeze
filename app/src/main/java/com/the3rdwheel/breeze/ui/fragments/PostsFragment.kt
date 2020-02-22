package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.the3rdwheel.breeze.BuildConfig
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.authentication.db.entity.Account
import kotlinx.android.synthetic.main.posts_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Credentials
import okio.IOException
import org.koin.android.ext.android.get
import timber.log.Timber

class PostsFragment : Fragment() {
    lateinit var database: AccountDatabase
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.posts_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val apiService = get<Auth>()
        CoroutineScope(IO).launch {
            database = get()
            try {

                val response = apiService.getAuthResponse(Credentials.basic(BuildConfig.CLIENT_ID, ""))
                database.accountDao().insert(Account("Anonymous", 0, response, 1))
            } catch (e: IOException) {
                Timber.e(e)
                try {
                    val text = database.accountDao().getUser("Anonymous").authResponse.access_token
                    withContext(Main) {
                        postTextView.text = text
                    }
                } catch (e: Exception) {
                    Timber.e(e)
                }

            }
        }
    }


}