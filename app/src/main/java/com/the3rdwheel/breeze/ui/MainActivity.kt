package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.databinding.ActivityMainBinding.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val binding = inflate(layoutInflater)
        val controller: NavController = findNavController(R.id.nav_host_fragment)
        binding.navBar.apply {
            setupWithNavController(controller)
        }
        binding.navBar.setOnNavigationItemReselectedListener {

        }
        setSupportActionBar(binding.toolbar.root as Toolbar)
        setContentView(binding.root)

    }


}
