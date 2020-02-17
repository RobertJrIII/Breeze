package com.the3rdwheel.breeze

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_main)
       // DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
    }
}
