package com.the3rdwheel.breeze.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.the3rdwheel.breeze.databinding.AccountFragmentBinding


class AccountFragment : Fragment() {
    private var _binding: AccountFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = AccountFragmentBinding.inflate(inflater, container, false)
        binding.accountTextView.text = "Account Fragment"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
