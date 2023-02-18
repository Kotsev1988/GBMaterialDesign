package com.example.gbmaterialdesign.data.Retrofits.MarsRetrofit

import com.example.gbmaterialdesign.data.Retrofits.NasaRetrofit.RetrofitClient
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.repository.MarsRepository
import retrofit2.Callback

class MarsRepositoryImpl(private val retrofitClient: RetrofitClient): MarsRepository {
    override fun getMarsPicture(callback: Callback<MarsPicture>) {
        retrofitClient.getMarsPicture(callback)
    }
}