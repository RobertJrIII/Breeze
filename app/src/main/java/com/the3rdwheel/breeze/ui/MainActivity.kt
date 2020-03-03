package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
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


//        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
//
//        val sharedPreferences = EncryptedSharedPreferences.create(
//            "secret_shared_prefs",
//            masterKeyAlias,
//            this,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )

    }


}
