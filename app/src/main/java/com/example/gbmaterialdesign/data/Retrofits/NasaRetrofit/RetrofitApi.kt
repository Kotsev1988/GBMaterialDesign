package com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit

import com.example.gbmaterialdesign.BuildConfig
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.PictureOfTheDay
import com.example.gbmaterialdesign.model.SolarSystemWeather.SolarSystemWeather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitApi {
    @GET("planetary/apod?")
    fun getPictureOfTheDay(
        @Query("api_key") apiKey : String = BuildConfig.NASA_API_KEY,
        @Query("date") date: String
    ): Call<PictureOfTheDay>



    @GET("mars-photos/api/v1/rovers/curiosity/photos?")
    fun getMarsPicture(
        @Query("api_key") apiKey : String = BuildConfig.NASA_API_KEY,
        @Query("sol") sol : String = "1000"
    ): Call<MarsPicture>

    @GET("DONKI/notifications?")
    fun SolarSystemWeather(
        @Query("api_key") apiKey : String = BuildConfig.NASA_API_KEY,
        @Query("type") type : String = "all",
        @Query("startDate") startDate: String,
        @Query("endDate") endDate: String
    ): Call<SolarSystemWeather>
}