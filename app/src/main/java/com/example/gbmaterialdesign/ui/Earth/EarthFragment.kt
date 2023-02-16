package com.example.gbmaterialdesign.ui.Earth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gbmaterialdesign.databinding.FragmentEarthBinding
import com.google.android.material.tabs.TabLayoutMediator


class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = ViewPagerAdapter(this)
        setTabs()

    }

    private fun setTabs() {
        TabLayoutMediator(binding.tabs, binding.viewPager){ tab, position ->

            tab.text = when(position){
                0 -> {
                    "1"
                }
                1 -> {
                    "2"
                }
                2 -> {
                    "3"
                }
                else ->{
                    ""
                }
            }
           // tab.text = "$position"

        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = EarthFragment()
    }
}
