package com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit

import com.example.gbmaterialdesign.model.PictureOfTheDay
import com.example.gbmaterialdesign.model.repository.Repository
import retrofit2.Callback

class RepositoryImpl(private val retrofitClient: RetrofitClient): Repository {

    override fun getPictureOfTheDay(date: String, callback: Callback<PictureOfTheDay>) {

        retrofitClient.getPictureOfTheDay(date, callback)
    }



}