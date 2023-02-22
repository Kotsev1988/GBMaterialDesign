package com.example.gbmaterialdesign.ui.SolarSystem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.gbmaterialdesign.R
import com.example.gbmaterialdesign.data.Retrofits.MarsRetrofit.MarsRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.data.Retrofits.SystemRetrofit.SolarSystemImpl
import com.example.gbmaterialdesign.databinding.FragmentMarsBinding
import com.example.gbmaterialdesign.databinding.FragmentSystemBinding
import com.example.gbmaterialdesign.model.PictureOfTheDay
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
import com.example.gbmaterialdesign.model.repository.MarsRepository
import com.example.gbmaterialdesign.model.repository.SolarSystemRepository
import com.example.gbmaterialdesign.ui.AppSatates.AppStateEarth
import com.example.gbmaterialdesign.ui.AppSatates.AppStateSolarSystem
import com.example.gbmaterialdesign.ui.Earth.DaysFragment
import com.example.gbmaterialdesign.ui.Mars.MarsFragment
import com.example.gbmaterialdesign.ui.PictureOfTheDay.PictureOfTheDayFragment
import com.example.gbmaterialdesign.ui.viewModel.EarthViewModel.EarthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class SystemFragment : Fragment() {


    private var _binding: FragmentSystemBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[SolarSystemViewModel::class.java]
    }

    val dateFormat: DateFormat = SimpleDateFormat("YYYY-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        return binding.root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       viewModel.getSolarSystemLiveData().observe(viewLifecycleOwner, Observer {
           renderData(it)
       })

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -2)
        val dayBeforeYesterday = dateFormat.format(calendar.time)

        calendar.add(Calendar.DATE, -1)
        val yesterday = dateFormat.format(calendar.time)

        viewModel.getEarthData(dayBeforeYesterday, yesterday)


    }

    private fun renderData(it: AppStateSolarSystem) {

        when (it) {
            is AppStateSolarSystem.Success -> {
               binding.frameLoadingSS.visibility = View.GONE

                binding.systemText.text = it.solarSystemWeather.get(0).messageBody

            }
            is AppStateSolarSystem.Error -> {


            }
            is AppStateSolarSystem.Loading -> {
                binding.frameLoadingSS.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = SystemFragment()
    }
}