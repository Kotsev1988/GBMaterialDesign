package com.example.gbmaterialdesign.ui.AppSatates

import com.example.gbmaterialdesign.model.PictureOfTheDay

sealed class AppState{
    data class Success(val pictureOfTheDay: PictureOfTheDay) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
