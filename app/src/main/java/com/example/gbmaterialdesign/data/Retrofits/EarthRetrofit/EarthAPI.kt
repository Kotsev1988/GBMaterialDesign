package com.example.gbmaterialdesign.data.Retrofits.EarthRetrofit


import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EarthAPI {
    @GET("api/natural/date/{date}")
    fun getEarthPicture(
        @Path("date") date: String
    ): Call<EarthPicture>
}