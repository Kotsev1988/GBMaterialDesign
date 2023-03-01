package com.example.gbmaterialdesign.ui.Nasa

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
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

    private val repositoryMars: MarsRepository = MarsRepositoryImpl(RetrofitClient())
    private val repositoryEarth: EarthRepository = EarthRepositoryImpl(RetrofitEarthClient())
    private val repository: SolarSystemRepository = SolarSystemImpl(RetrofitClient())
    val dateFormat: DateFormat = SimpleDateFormat("YYYY-MM-dd")

    private var _binding: FragmentNasaBinding? = null
    private val binding get() = _binding!!

    private var dataList: MutableList<Pair<Data, Boolean>> = mutableListOf()

    val callbackAdd = object : AddItems {
        override fun add(position: Int) {

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -3)
            val day = dateFormat.format(calendar.time)

            CoroutineScope(Dispatchers.IO).launch {

                val response = repositoryEarth.getEarthPictureRecycler(day)
                val earth_body = response.body()?.get(0)

                withContext(Dispatchers.Main) {
                    dataList.add(position, Pair(Data(EARTH, earth_body, null, null),false))
                    adapter.addItemList(dataList, position)
                }

            }
        }
    }

    val callbackRemove = object : RemoveItem {
        override fun remove(position: Int) {
            dataList.removeAt(position)
            adapter.removeItemList(dataList, position)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentNasaBinding.inflate(inflater, container, false)
        return binding.root
    }

    lateinit var adapter: NasaRecyclerAdapter

    lateinit var response: Response<EarthPicture>

    lateinit var solarResponse: Response<SolarSystemWeather>
    lateinit var marsResponse: Response<MarsPicture>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -2)
        val day = dateFormat.format(calendar.time)

        CoroutineScope(Dispatchers.IO).launch {

             response = repositoryEarth.getEarthPictureRecycler(day)
            dataList.add(Pair(Data(HEADER, null, null, null),false))


            for (i in 0..5){
                dataList.add(Pair(Data(EARTH, response.body()?.get(i),null,  null),false))
            }

            solarResponse = repository.getSolarsystemWeatherRecycler("2014-05-01", "2014-05-08")
            dataList.add(Pair(Data(HEADER, null, null, null), false))

            for (i in 0..5){
                dataList.add(Pair(Data(SOLAR, null,null,  solarResponse.body()?.get(i)), false))
            }


             marsResponse = repositoryMars.getMarsPictureRecycler()
            dataList.add(Pair(Data(HEADER, null, null, null),false))

            for (i in 0..5){
                dataList.add(Pair(Data(MARS, null, marsResponse.body()?.photos?.get(i),  null),false))
            }


            withContext(Dispatchers.Main){
                adapter = NasaRecyclerAdapter(dataList, callbackAdd, callbackRemove)
                binding.recyclerView.adapter = adapter
                ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.recyclerView)
            }
        }

        binding.refreshList.setOnClickListener {

            val newDataList: MutableList<Pair<Data, Boolean>> = mutableListOf()

            newDataList.add(Pair(Data(HEADER, null, null, null),false))
            for (i in 6..12){
                newDataList.add(Pair(Data(EARTH, response.body()?.get(i),null,  null),false))
            }

            newDataList.add(Pair(Data(HEADER, null, null, null), false))

            for (i in 6..11){
                newDataList.add(Pair(Data(SOLAR, null,null,  solarResponse.body()?.get(i)), false))
            }
            newDataList.add(Pair(Data(HEADER, null, null, null), false))
            for (i in 6..11){
                newDataList.add(Pair(Data(MARS, null, marsResponse.body()?.photos?.get(i),  null),false))
            }

            adapter.setDataLIstDiffUtil(newDataList)
            dataList = newDataList

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