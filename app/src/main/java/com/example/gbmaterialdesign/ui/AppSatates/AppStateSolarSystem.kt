package com.example.gbmaterialdesign.ui.AppSatates

import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.PictureOfTheDay
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather

sealed class AppStateSolarSystem{
    data class Success(val solarSystemWeather: SolarSystemWeather) : AppStateSolarSystem()
    data class Error(val error: Throwable) : AppStateSolarSystem()
    object Loading : AppStateSolarSystem()
}
