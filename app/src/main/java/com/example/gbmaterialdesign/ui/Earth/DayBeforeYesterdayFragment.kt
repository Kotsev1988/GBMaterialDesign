package com.example.gbmaterialdesign.ui.Earth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gbmaterialdesign.databinding.FragmentDayBeforeYesterdayBinding
import com.example.gbmaterialdesign.databinding.FragmentEarthBinding
import com.google.android.material.tabs.TabLayoutMediator


class DayBeforeYesterdayFragment : Fragment() {

    private var _binding: FragmentDayBeforeYesterdayBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentDayBeforeYesterdayBinding.inflate(inflater, container, false)
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
        fun newInstance() = DayBeforeYesterdayFragment()
    }
}
