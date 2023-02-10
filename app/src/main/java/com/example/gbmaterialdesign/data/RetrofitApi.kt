package com.example.gbmaterialdesign.data

import com.example.gbmaterialdesign.BuildConfig
import com.example.gbmaterialdesign.model.PictureOfTheDay
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("planetary/apod?")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey : String = BuildConfig.NASA_API_KEY,
        @Query("date") date: String
    ): Call<PictureOfTheDay>
}