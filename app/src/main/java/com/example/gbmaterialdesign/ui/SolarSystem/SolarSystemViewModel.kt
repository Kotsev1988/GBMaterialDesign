package com.example.gbmaterialdesign.ui.SolarSystem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.EarthRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.RetrofitEarthClient
import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.data.Retrofits.SystemRetrofit.SolarSystemImpl
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
import com.example.gbmaterialdesign.model.repository.EarthRepository
import com.example.gbmaterialdesign.model.repository.SolarSystemRepository
import com.example.gbmaterialdesign.ui.AppSatates.AppStateEarth
import com.example.gbmaterialdesign.ui.AppSatates.AppStateSolarSystem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SolarSystemViewModel(
    private val repository: SolarSystemRepository = SolarSystemImpl(RetrofitClient())
):ViewModel() {

    private val liveSolarSystem: MutableLiveData<AppStateSolarSystem> = MutableLiveData()
    fun getSolarSystemLiveData(): LiveData<AppStateSolarSystem> = liveSolarSystem

    private val callback: Callback<SolarSystemWeather> =object : Callback<SolarSystemWeather> {
        override fun onResponse(call: Call<SolarSystemWeather>, response: Response<SolarSystemWeather>) {

            liveSolarSystem.postValue(response.body()?.let {
                AppStateSolarSystem.Success(it)
            })
        }

        override fun onFailure(call: Call<SolarSystemWeather>, t: Throwable) {

            liveSolarSystem.postValue(
                AppStateSolarSystem.Error(Throwable("Error"))
            )
        }
    }


    fun getEarthData(startDate:String, endDate:String){
        repository.getSolarsystemWeather(startDate, endDate, callback)
    }
}