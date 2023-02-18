package com.example.gbmaterialdesign.model.repository

import com.example.gbmaterialdesign.model.PictureOfTheDay
import retrofit2.Callback

interface Repository {
    fun getPictureOfTheDay(date: String, callback: Callback<PictureOfTheDay>)
}