package com.example.toobometeo.models

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("hourly") val hourly: Hourly
)

data class Hourly(
    @SerializedName("time") val time:  List<String>,
    @SerializedName("temperature_2m") val temperature_2m:  List<Double>,
    @SerializedName("weathercode") val weathercode: List<Double>,
    @SerializedName("windspeed_180m") val windspeed_180m:  List<Double>,
)

