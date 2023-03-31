package com.example.toobometeo.viewmodels

import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ayush.retrofitexample.RetrofitHelper
import com.example.toobometeo.R
import com.example.toobometeo.models.WeatherResponse
import com.example.toobometeo.models.localisationResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*


interface OpenMeteoApi {
    @GET("forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String= "temperature_2m,weathercode,windspeed_180m",
        @Query("daily") daily: String,
        @Query("timezone") timezone: String
    ): Response<WeatherResponse>
}

interface adressGouvApi {
    @GET("search/")
    suspend fun getSearch(
        @Query("q") q: String
    ): Response<localisationResponse>
}


