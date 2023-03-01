package com.example.gbmaterialdesign.ui.AppSatates

import com.example.gbmaterialdesign.model.Data.Data
import com.example.gbmaterialdesign.model.EarthPictures.EarthPicture
import com.example.gbmaterialdesign.model.PictureOfTheDay

sealed class AppStateNasa{
    data class Success(val data: MutableList<Pair<Data, Boolean>>) : AppStateNasa()
    data class UseDiffUtil(val newData: MutableList<Pair<Data, Boolean>>) : AppStateNasa()
    data class AddItem(val data: MutableList<Pair<Data, Boolean>>, val position: Int) : AppStateNasa()
    data class RemoveItem(val data: MutableList<Pair<Data, Boolean>>, val position: Int) : AppStateNasa()
    data class Error(val error: Throwable) : AppStateNasa()
    object Loading : AppStateNasa()
}
