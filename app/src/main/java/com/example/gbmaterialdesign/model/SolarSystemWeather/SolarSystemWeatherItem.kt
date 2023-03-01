package com.example.gbmaterialdesign.model.SolarSystemWeather

import com.google.gson.annotations.SerializedName

data class SolarSystemWeatherItem(
    val messageBody: String,
    @SerializedName("messageID")
    val id: String,
    val messageIssueTime: String,
    val messageType: String,
    val messageURL: String
)