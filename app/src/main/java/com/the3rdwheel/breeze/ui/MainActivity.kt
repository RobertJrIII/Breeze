package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.databinding.ActivityMainBinding
import com.the3rdwheel.breeze.viewmodel.CommunicationViewModel

const val reselected = true

class MainActivity : AppCompatActivity() {

    private val sharedViewModel: CommunicationViewModel by viewModels()
    private lateinit var controller: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)
        controller = findNavController(R.id.nav_host_fragment)


        binding.navBar.setupWithNavController(controller)

        binding.navBar.setOnNavigationItemReselectedListener {
            if (it.itemId == R.id.postsFragment) {
                sharedViewModel.setPostFragmentReselected(reselected)
            }
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
        if (item.itemId == R.id.settings){
            controller.navigate(R.id.settingsFragment)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
