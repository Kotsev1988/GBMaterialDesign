package com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit


import com.example.gbmaterialdesign.BuildConfig
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EarthAPI {
    @GET("api/natural/date/{date}")
    fun getEarthPicture(
        @Path("date") date: String,
        @Query("api_key") apiKey : String = BuildConfig.NASA_API_KEY
    ): Call<EarthPicture>

    @GET("api/natural/date/{date}")
    suspend fun getEarthPictureRecycler(
        @Path("date") date: String,
        @Query("api_key") apiKey : String
    ): Response<EarthPicture>

}