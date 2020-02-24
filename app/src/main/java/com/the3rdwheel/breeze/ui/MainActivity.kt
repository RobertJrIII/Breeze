package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.the3rdwheel.breeze.R
import kotlinx.android.synthetic.main.activity_main.*

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


}
