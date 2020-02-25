package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.toolbar.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        val controller: NavController = findNavController(R.id.nav_host_fragment)
        val navBar = binding.navBar
        navBar.apply {
            setupWithNavController(controller)
        }
        navBar.setOnNavigationItemReselectedListener {

        }
        setSupportActionBar(binding.mainToolbar.toolbar)


    }


}
