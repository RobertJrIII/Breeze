package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        val controller: NavController = findNavController(R.id.nav_host_fragment)
        val navBar = binding.navBar

        navBar.setupWithNavController(controller)

        navBar.setOnNavigationItemReselectedListener {
//TODO Add reclicking feature
        }
        setSupportActionBar(binding.mainToolbar.toolbar)


        val appBarConfiguration = AppBarConfiguration(

            topLevelDestinationIds = setOf(
                R.id.postsFragment,
                R.id.accountFragment, R.id.inboxFragment,
                R.id.settingsFragment

            )
        )

        setupActionBarWithNavController(controller, appBarConfiguration)


    }


}
