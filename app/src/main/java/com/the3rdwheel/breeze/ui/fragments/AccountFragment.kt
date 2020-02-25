package com.the3rdwheel.breeze.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.the3rdwheel.breeze.databinding.AccountFragmentBinding


/**
 * A simple [Fragment] subclass.
 */
class AccountFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = AccountFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}
