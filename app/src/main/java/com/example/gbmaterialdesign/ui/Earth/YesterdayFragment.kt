package com.example.gbmaterialdesign.ui.Earth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gbmaterialdesign.databinding.FragmentEarthBinding
import com.example.gbmaterialdesign.databinding.FragmentYesterdayBinding
import com.google.android.material.tabs.TabLayoutMediator


class YesterdayFragment : Fragment() {

    private var _binding: FragmentYesterdayBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentYesterdayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = YesterdayFragment()
    }
}
