package com.example.gbmaterialdesign.ui.AppSatates

import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.PictureOfTheDay

sealed class AppStateEarth{
    data class Success(val earthPicture: EarthPicture) : AppStateEarth()
    data class Error(val error: Throwable) : AppStateEarth()
    object Loading : AppStateEarth()
}
