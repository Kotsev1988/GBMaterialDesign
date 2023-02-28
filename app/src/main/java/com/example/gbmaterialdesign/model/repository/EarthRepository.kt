package com.example.gbmaterialdesign.model.repository


import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.PictureOfTheDay
import retrofit2.Callback
import retrofit2.Response


interface EarthRepository {
    fun getEarthPicture(date: String, callback: Callback<EarthPicture>)
    suspend fun getEarthPictureRecycler(date: String): Response<EarthPicture>
}