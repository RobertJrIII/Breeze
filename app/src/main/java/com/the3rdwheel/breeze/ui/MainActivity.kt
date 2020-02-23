package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.authentication.api.Auth
import com.the3rdwheel.breeze.authentication.db.AccountDatabase
import com.the3rdwheel.breeze.authentication.db.entity.Account
import com.the3rdwheel.breeze.reddit.RedditUtils
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import okio.IOException
import org.koin.android.ext.android.get
import timber.log.Timber

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val controller: NavController = findNavController(R.id.nav_host_fragment)
        navBar.apply {
            setupWithNavController(controller)
        }
        navBar.setOnNavigationItemReselectedListener {

        }
        setSupportActionBar(toolbar)


    }
//
//    private fun setUser() {
//        CoroutineScope(IO).launch {
//
//
//            if (database.accountDao().getAnyUser() == null) {
//
//
//                try {
//                    val response =
//                        auth.getAuthResponse(RedditUtils.CREDENTIALS)
//                    database.accountDao()
//                        .insert(
//                            Account(
//                                RedditUtils.ANONYMOUS_USER,
//                                RedditUtils.ANONYMOUS_KARMA,
//                                response,
//                                RedditUtils.CURRENT_USER
//                            )
//                        )
//
//                    database.close()
//                } catch (e: IOException) {
//                    Timber.e(e)
//                }
//
//
//            }
//
//        }
//    }


}
