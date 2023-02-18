package com.example.gbmaterialdesign.model.repository


import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import retrofit2.Callback


interface EarthRepository {
    fun getEarthPicture(date: String, callback: Callback<EarthPicture>)
}