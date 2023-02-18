package com.example.gbmaterialdesign.ui.AppSatates

import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.MarsPictures.MarsPicture
import com.example.gbmaterialdesign.model.PictureOfTheDay

sealed class AppStateMars{
    data class Success(val marsPicture: MarsPicture) : AppStateMars()
    data class Error(val error: Throwable) : AppStateMars()
    object Loading : AppStateMars()
}
