package com.example.gbmaterialdesign.model.repository

import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import retrofit2.Callback

interface MarsRepository {
    fun getMarsPicture(callback: Callback<MarsPicture>)
}