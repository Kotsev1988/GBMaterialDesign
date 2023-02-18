package com.example.gbmaterialdesign.ui.Mars

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.bumptech.glide.Glide
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.EarthRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.MarsRetrofit.MarsRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.databinding.FragmentMarsBinding
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.repository.EarthRepository
import com.example.gbmaterialdesign.model.repository.MarsRepository
import com.example.gbmaterialdesign.ui.AppSatates.AppStateEarth
import com.example.gbmaterialdesign.ui.AppSatates.AppStateMars
import com.example.gbmaterialdesign.ui.viewModel.EarthViewModel.EarthViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsFragment : Fragment() {

    private val viewModel by lazy {
        ViewModelProvider(this)[MarsViewModel::class.java]
    }

    private var _binding: FragmentMarsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentMarsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getMarsLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })

        viewModel.getMarsPicture()
    }

    private fun renderData(it: AppStateMars) {

        when (it) {
            is AppStateMars.Success -> {
                binding.frameLoadingMars.visibility = View.GONE

                val picture = it.marsPicture.photos?.get(0)?.img_src
                binding.marsRover.text = it.marsPicture.photos?.get(0)?.rover?.name.toString()

                binding.marsPicture.load(
                    picture
                ){
                    lifecycle(this@MarsFragment)
                }
            }
            is AppStateMars.Error -> {


            }
            is AppStateMars.Loading -> {
                binding.frameLoadingMars.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = MarsFragment()
    }
}