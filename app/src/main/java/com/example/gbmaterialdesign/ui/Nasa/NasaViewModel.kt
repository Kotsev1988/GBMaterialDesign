package com.example.gbmaterialdesign.ui.Nasa

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.EarthRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.RetrofitEarthClient
import com.example.gbmaterialdesign.data.Retrofits.MarsRetrofit.MarsRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.data.Retrofits.SystemRetrofit.SolarSystemImpl
import com.example.gbmaterialdesign.model.Data.Data
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
import com.example.gbmaterialdesign.model.repository.EarthRepository
import com.example.gbmaterialdesign.model.repository.MarsRepository
import com.example.gbmaterialdesign.model.repository.SolarSystemRepository
import com.example.gbmaterialdesign.ui.AppSatates.AppStateNasa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NasaViewModel (
    private val repositoryMars: MarsRepository = MarsRepositoryImpl(RetrofitClient()),
            private val repositoryEarth: EarthRepository = EarthRepositoryImpl(RetrofitEarthClient()),
            private val repository: SolarSystemRepository = SolarSystemImpl(RetrofitClient())
): ViewModel() {

    private val dateFormat: DateFormat = SimpleDateFormat("YYYY-MM-dd")

    private var dataList: MutableList<Pair<Data, Boolean>> = mutableListOf()
    private var newDataList: MutableList<Pair<Data, Boolean>> = mutableListOf()

    lateinit var response: Response<EarthPicture>

    lateinit var solarResponse: Response<SolarSystemWeather>
    lateinit var marsResponse: Response<MarsPicture>


    private val liveDataNasa: MutableLiveData<AppStateNasa> = MutableLiveData()
    fun getNasaLiveData(): LiveData<AppStateNasa> = liveDataNasa

    fun getLists(){
        viewModelScope.launch {


            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DATE, -10)
            val day = dateFormat.format(calendar.time)

            response = repositoryEarth.getEarthPictureRecycler(day)
            dataList.add(Pair(Data(Data.HEADER, null, null, null),false))
            newDataList.add(Pair(Data(Data.HEADER, null, null, null),false))


            if (response.isSuccessful) {
                for (i in 0..5) {
                    dataList.add(Pair(Data(Data.EARTH, response.body()?.get(i), null, null), false))
                    newDataList.add(Pair(Data(Data.EARTH, response.body()?.get(i), null, null), false))

                }
            }

            solarResponse = repository.getSolarsystemWeatherRecycler("2014-05-01", "2014-05-08")
            dataList.add(Pair(Data(Data.HEADER, null, null, null), false))
            newDataList.add(Pair(Data(Data.HEADER, null, null, null),false))

            for (i in 0..5){
                dataList.add(Pair(Data(Data.SOLAR, null,null,  solarResponse.body()?.get(i)), false))
                newDataList.add(Pair(Data(Data.SOLAR, null,null,  solarResponse.body()?.get(i)), false))
            }


            marsResponse = repositoryMars.getMarsPictureRecycler()
            dataList.add(Pair(Data(Data.HEADER, null, null, null),false))
            newDataList.add(Pair(Data(Data.HEADER, null, null, null),false))

            for (i in 0..5){
                dataList.add(Pair(Data(Data.MARS, null, marsResponse.body()?.photos?.get(i),  null),false))
                newDataList.add(Pair(Data(Data.MARS, null, marsResponse.body()?.photos?.get(i),  null),false))
            }

            withContext(Dispatchers.Main) {
                liveDataNasa.postValue(AppStateNasa.Success(dataList))
            }
        }
    }

    fun useDiffUtil(){

        newDataList.get(2).first.earth?.id = "1235"
        newDataList.get(4).first.earth?.id = "1238888"

        if (response.isSuccessful) {

                newDataList.add(Pair(Data(Data.EARTH, response.body()?.get(6), null, null), false))


        }


        //dataList = newDataList - если сравниваемые списки не равны пока не сделаю это равентсво
        // корректно список не отображается. То есть список обновляется, но когда удаляешь элемент
        // или добавляешь - появляется старый список
        // И еще почему то если я в этой функции создам val newDataList = dataList и потом меняю
        // значения у newDataList , diffutil не срабатывает

       // dataList = newDataList
        liveDataNasa.postValue(AppStateNasa.UseDiffUtil(newDataList))
    }

    fun removeItem(position: Int){
        dataList.removeAt(position)
        liveDataNasa.postValue(AppStateNasa.RemoveItem(dataList, position))

    }

    fun addItem(position: Int){
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, -3)
        val day = dateFormat.format(calendar.time)

        viewModelScope.launch {

            val response = repositoryEarth.getEarthPictureRecycler(day)
            val earthBody = response.body()?.get(0)

            dataList.add(position, Pair(Data(Data.EARTH, earthBody, null, null),false))

            withContext(Dispatchers.Main) {
                liveDataNasa.postValue(AppStateNasa.AddItem(dataList, position))
            }
        }
    }
}