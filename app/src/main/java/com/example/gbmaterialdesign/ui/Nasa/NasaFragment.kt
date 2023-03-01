package com.example.gbmaterialdesign.ui.Nasa

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.transition.*
import coil.load
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.EarthRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.RetrofitEarthClient
import com.example.gbmaterialdesign.data.Retrofits.MarsRetrofit.MarsRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.data.Retrofits.SystemRetrofit.SolarSystemImpl
import com.example.gbmaterialdesign.databinding.FragmentNasaBinding
import com.example.gbmaterialdesign.model.Data.Data
import com.example.gbmaterialdesign.model.Data.Data.Companion.EARTH
import com.example.gbmaterialdesign.model.Data.Data.Companion.HEADER
import com.example.gbmaterialdesign.model.Data.Data.Companion.MARS
import com.example.gbmaterialdesign.model.Data.Data.Companion.SOLAR
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
import com.example.gbmaterialdesign.model.repository.EarthRepository
import com.example.gbmaterialdesign.model.repository.MarsRepository
import com.example.gbmaterialdesign.model.repository.SolarSystemRepository
import com.example.gbmaterialdesign.ui.AppSatates.AppStateEarth
import com.example.gbmaterialdesign.ui.AppSatates.AppStateNasa
import com.example.gbmaterialdesign.ui.Earth.EarthViewModel
import com.example.gbmaterialdesign.ui.TouchHelper.ItemTouchHelperCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class NasaFragment : Fragment() {

    private var _binding: FragmentNasaBinding? = null
    private val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this)[NasaViewModel::class.java]
    }

    private val callbackAdd = object : AddItems {
        override fun add(position: Int) {
            viewModel.addItem(position)
        }
    }

    private val callbackRemove = object : RemoveItem {
        override fun remove(position: Int) {
            viewModel.removeItem(position)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNasaBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var adapter: NasaRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getNasaLiveData().observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            renderData(it)
        })

        viewModel.getLists()

        binding.refreshList.setOnClickListener {
            viewModel.useDiffUtil()
        }
    }

    private fun renderData(it: AppStateNasa) {

        when (it) {
            is AppStateNasa.Success -> {
                adapter = NasaRecyclerAdapter(it.data, callbackAdd, callbackRemove)
                binding.recyclerView.adapter = adapter
                ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
            }

            is AppStateNasa.AddItem -> {
                adapter.addItemList(it.data, it.position)
            }

            is AppStateNasa.RemoveItem -> {
                adapter.removeItemList(it.data, it.position)

            }

            is AppStateNasa.UseDiffUtil -> {

                adapter.setDataLIstDiffUtil(it.newData)

            }

            is AppStateNasa.Error -> {


            }
            is AppStateNasa.Loading -> {

            }
        }
    }

    companion object {
        const val BUNDLE_NASA = "Nasa"
        fun newInstance(bundle: Bundle): NasaFragment {

            val fragment = NasaFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}