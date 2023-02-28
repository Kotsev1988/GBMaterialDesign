package com.example.gbmaterialdesign.model.repository

import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
import retrofit2.Callback
import retrofit2.Response

interface SolarSystemRepository {
    fun getSolarsystemWeather(startDate: String, endDate: String, callback: Callback<SolarSystemWeather>)
    suspend fun getSolarsystemWeatherRecycler(startDate: String, endDate: String): Response<SolarSystemWeather>
}