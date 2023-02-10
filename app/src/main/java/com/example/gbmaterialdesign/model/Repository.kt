package com.example.gbmaterialdesign.model

import retrofit2.Callback

interface Repository {
    fun getPictureOfTheDay(date: String, callback: Callback<PictureOfTheDay>)
}