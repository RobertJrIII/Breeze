package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.the3rdwheel.breeze.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val controller: NavController = findNavController(R.id.nav_host_fragment)
        navBar.apply {
            setupWithNavController(controller)
        }

        navBar.setOnNavigationItemReselectedListener {
            Toast.makeText(this, "That Tickles!", Toast.LENGTH_SHORT).show()
        }
        setSupportActionBar(toolbar)

    }
}
