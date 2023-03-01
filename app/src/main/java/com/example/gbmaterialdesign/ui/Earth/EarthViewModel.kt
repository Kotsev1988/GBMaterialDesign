package com.example.gbmaterialdesign.ui.Earth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.EarthRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit.RetrofitEarthClient
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.repository.EarthRepository
import com.example.gbmaterialdesign.ui.AppSatates.AppStateEarth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EarthViewModel(
    private val repository: EarthRepository = EarthRepositoryImpl(RetrofitEarthClient())
): ViewModel() {

    private val liveDataEarth: MutableLiveData<AppStateEarth> = MutableLiveData()
    fun getEarthLiveData(): LiveData<AppStateEarth> = liveDataEarth

    private val callback: Callback<EarthPicture> =object : Callback<EarthPicture> {
        override fun onResponse(call: Call<EarthPicture>, response: Response<EarthPicture>) {

            liveDataEarth.postValue(response.body()?.let {
                AppStateEarth.Success(it)
            })
        }

        override fun onFailure(call: Call<EarthPicture>, t: Throwable) {

            liveDataEarth.postValue(
                AppStateEarth.Error(Throwable("Error"))
            )
        }
    }


    fun getEarthData(date:String){
        repository.getEarthPicture(date, callback)
    }

}