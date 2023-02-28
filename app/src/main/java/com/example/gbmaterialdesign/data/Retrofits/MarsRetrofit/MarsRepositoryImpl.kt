package com.example.gbmaterialdesign.data.Retrofits.MarsRetrofit

import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.repository.MarsRepository
import retrofit2.Callback
import retrofit2.Response

class MarsRepositoryImpl(private val retrofitClient: RetrofitClient): MarsRepository {
    override fun getMarsPicture(callback: Callback<MarsPicture>) {
        retrofitClient.getMarsPicture(callback)
    }

    override suspend fun getMarsPictureRecycler(): Response<MarsPicture> {
        return retrofitClient.getMarsPictureRecycler()
    }
}