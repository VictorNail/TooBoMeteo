package com.example.toobometeo.repository

import androidx.annotation.WorkerThread
import com.example.toobometeo.dao.WeatherCityDao
import com.example.toobometeo.entity.WeatherCityEntity
import kotlinx.coroutines.flow.Flow

class weatherCityRepository(private val wordDao: WeatherCityDao) {
    val allCities: Flow<List<WeatherCityEntity>> = wordDao.getWeatherCities()

    @WorkerThread
    suspend fun insert(city: WeatherCityEntity) {
        wordDao.insert(city)
    }
}