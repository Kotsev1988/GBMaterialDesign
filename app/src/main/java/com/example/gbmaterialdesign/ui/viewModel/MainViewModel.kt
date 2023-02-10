package com.example.gbmaterialdesign.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gbmaterialdesign.data.RepositoryImpl
import com.example.gbmaterialdesign.data.RetrofitClient
import com.example.gbmaterialdesign.model.PictureOfTheDay
import com.example.gbmaterialdesign.model.Repository
import com.example.gbmaterialdesign.ui.AppSatates.AppState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(
    private val repository: Repository = RepositoryImpl(RetrofitClient()),
) : ViewModel() {

    private val liveDataPictureOfTheDay: MutableLiveData<AppState> = MutableLiveData()
    fun getPictureLiveData(): LiveData<AppState> = liveDataPictureOfTheDay

    private val callback = object : Callback<PictureOfTheDay> {
        override fun onResponse(call: Call<PictureOfTheDay>, response: Response<PictureOfTheDay>) {
            if (response.isSuccessful) {

                liveDataPictureOfTheDay.postValue(response.body()?.let { AppState.Success(it) })
            } else {

                liveDataPictureOfTheDay.postValue(AppState.Error(Throwable("Error")))
            }
        }

        override fun onFailure(call: Call<PictureOfTheDay>, t: Throwable) {

            liveDataPictureOfTheDay.postValue(AppState.Error(Throwable("Failure")))
        }
    }

    fun sendRequest(date: String) {
        liveDataPictureOfTheDay.value = AppState.Loading
        repository.getPictureOfTheDay(date, callback)
    }
}