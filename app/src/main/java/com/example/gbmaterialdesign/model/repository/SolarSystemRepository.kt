package com.example.gbmaterialdesign.model.repository

import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
import retrofit2.Callback

interface SolarSystemRepository {
    fun getSolarsystemWeather(startDate: String, endDate: String, callback: Callback<SolarSystemWeather>)
}