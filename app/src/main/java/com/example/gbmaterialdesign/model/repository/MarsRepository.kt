package com.example.gbmaterialdesign.model.repository

import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import retrofit2.Callback
import retrofit2.Response

interface MarsRepository {
    fun getMarsPicture(callback: Callback<MarsPicture>)
    suspend fun getMarsPictureRecycler(): Response<MarsPicture>
}