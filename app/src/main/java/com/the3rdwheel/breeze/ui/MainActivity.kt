package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
                R.id.postsFragment, R.id.subsFragment,
                R.id.accountFragment, R.id.inboxFragment,
                R.id.searchFragment

            )
        )

        setupActionBarWithNavController(controller, appBarConfiguration)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.settings -> {

                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
