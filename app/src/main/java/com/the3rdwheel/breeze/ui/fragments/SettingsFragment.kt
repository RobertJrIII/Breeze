package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import android.view.View

import androidx.preference.PreferenceFragmentCompat
import com.the3rdwheel.breeze.R

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        
        super.onViewCreated(view, savedInstanceState)
    }


}
