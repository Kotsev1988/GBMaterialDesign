package com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit

import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.PictureOfTheDay
import com.example.gbmaterialdesign.model.repository.EarthRepository
import retrofit2.Callback
import retrofit2.Response

class EarthRepositoryImpl(private val retrofitClient: RetrofitEarthClient): EarthRepository {
    override fun getEarthPicture(date: String, callback: Callback<EarthPicture>) {
            retrofitClient.getEarthPicture(date, callback)
    }

    override suspend fun getEarthPictureRecycler(date: String): Response<EarthPicture> {
        return retrofitClient.getEarthPictureRecycler(date)
    }
}