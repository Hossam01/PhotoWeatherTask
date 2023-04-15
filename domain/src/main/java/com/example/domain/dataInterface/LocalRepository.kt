package com.example.domain.dataInterface

import com.example.domain.models.WeatherPhoto

interface LocalRepository {
    fun setAddWeather(model: WeatherPhoto)
    suspend fun getWeather():List<WeatherPhoto?>?
}