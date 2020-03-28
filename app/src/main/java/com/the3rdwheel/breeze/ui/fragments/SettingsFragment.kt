package com.the3rdwheel.breeze.ui.fragments

import android.os.Bundle
import android.view.Menu

import androidx.preference.PreferenceFragmentCompat
import com.the3rdwheel.breeze.R

class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.settings).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

}
