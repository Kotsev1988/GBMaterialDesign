package com.example.gbmaterialdesign.data.Retrofits.SystemRetrofit

import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
import com.example.gbmaterialdesign.model.repository.SolarSystemRepository
import retrofit2.Callback

class SolarSystemImpl(private val retrofitClient: RetrofitClient): SolarSystemRepository {
    override fun getSolarsystemWeather(
        startDate: String,
        endDate: String,
        callback: Callback<SolarSystemWeather>,
    ) {
        retrofitClient.getSolarSystemWeather(startDate, endDate, callback)
    }
}