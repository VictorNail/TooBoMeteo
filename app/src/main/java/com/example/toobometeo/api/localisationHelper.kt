package com.ayush.retrofitexample

import android.widget.Toast
import com.example.toobometeo.models.WeatherResponse
import com.example.toobometeo.viewmodels.OpenMeteoApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object localisationHelper {

    val baseUrl = "https://api-adresse.data.gouv.fr/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
