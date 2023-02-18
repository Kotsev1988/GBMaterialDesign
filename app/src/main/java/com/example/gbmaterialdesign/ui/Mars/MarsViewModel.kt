package com.example.gbmaterialdesign.ui.Mars

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.load
import com.example.gbmaterialdesign.data.Retrofits.MarsRetrofit.MarsRepositoryImpl
import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.repository.MarsRepository
import com.example.gbmaterialdesign.ui.AppSatates.AppStateEarth
import com.example.gbmaterialdesign.ui.AppSatates.AppStateMars
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarsViewModel(
    private val repository: MarsRepository = MarsRepositoryImpl(RetrofitClient())
): ViewModel() {
    private val liveDataMars: MutableLiveData<AppStateMars> = MutableLiveData()
    fun getMarsLiveData(): LiveData<AppStateMars> = liveDataMars

    fun getMarsPicture(){
        repository.getMarsPicture( object : Callback<MarsPicture> {
            override fun onResponse(call: Call<MarsPicture>, response: Response<MarsPicture>) {
                if (response.isSuccessful) {

                    liveDataMars.postValue(response.body()?.let {
                        AppStateMars.Success(it)
                    })
                }
            }

            override fun onFailure(call: Call<MarsPicture>, t: Throwable) {

                liveDataMars.postValue(
                    AppStateMars.Error(Throwable("Error"))
                )
            }
        })
    }
}