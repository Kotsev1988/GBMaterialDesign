package com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit

import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.repository.EarthRepository
import retrofit2.Callback

class EarthRepositoryImpl(private val retrofitClient: RetrofitEarthClient): EarthRepository {
    override fun getEarthPicture(date: String, callback: Callback<EarthPicture>) {
            retrofitClient.getEarthPicture(date, callback)
    }
}