package com.example.gbmaterialdesign.model.Data

import com.example.gbmaterialdesign.model.EarthPictures.EarthPictureItem
import com.example.gbmaterialdesign.model.MarsPictures.Photo
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeatherItem

class Data(val type: Int = EARTH, val earth: EarthPictureItem?, val mars: Photo?, val solar: SolarSystemWeatherItem?) {

    companion object{
        const val EARTH =0
        const val MARS = 1
        const val SOLAR = 2
        const val HEADER = 3
    }
}