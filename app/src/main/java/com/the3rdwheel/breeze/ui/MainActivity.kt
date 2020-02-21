package com.the3rdwheel.breeze.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.the3rdwheel.breeze.R
import com.the3rdwheel.breeze.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val controller: NavController = findNavController(R.id.nav_host_fragment)
        val navBar = binding.navBar.apply {
            setupWithNavController(controller)
        }

        navBar.setOnNavigationItemReselectedListener {
            Toast.makeText(this, "That Tickles!", Toast.LENGTH_SHORT).show()
        }
        setSupportActionBar(toolbar)

    }
}
