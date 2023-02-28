package com.example.gbmaterialdesign

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.gbmaterialdesign.databinding.BaseFragmentBinding
import com.example.gbmaterialdesign.ui.Earth.EarthFragment
import com.example.gbmaterialdesign.ui.Mars.MarsFragment
import com.example.gbmaterialdesign.ui.Nasa.NasaFragment
import com.example.gbmaterialdesign.ui.PictureOfTheDay.PictureOfTheDayFragment
import com.example.gbmaterialdesign.ui.SolarSystem.SystemFragment


class BaseFragment(): Fragment() {

    private var _binding: BaseFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = BaseFragmentBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.bottom_home ->{
                    navigateTo(PictureOfTheDayFragment())
                    true
                }
                R.id.bottom_earth ->{
                    navigateTo(EarthFragment())
                    true
                }

                R.id.bottom_mars ->{
                    navigateTo(MarsFragment())
                    true
                }

                R.id.bottom_system ->{
                    navigateTo(SystemFragment())
                    true
                }

                R.id.nasa_list ->{
                    navigateTo(NasaFragment())
                    true
                }
                else -> true
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.bottom_home
    }

    private fun navigateTo(fragment: Fragment) {

        childFragmentManager.beginTransaction()
            .replace(R.id.baseFragmentContainer, fragment)
            .addToBackStack("")
            .commit()
    }
}