package com.the3rdwheel.breeze.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.the3rdwheel.breeze.databinding.InboxFragmentBinding


class InboxFragment : Fragment() {

    private var _binding: InboxFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = InboxFragmentBinding.inflate(inflater, container, false)
        binding.inboxTextView.text = "Inbox Fragment"
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
