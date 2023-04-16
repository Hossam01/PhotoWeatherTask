package com.example.domain.dataInterface

import com.example.domain.models.WeatherPhoto

interface LocalRepository {
    fun setAddWeather(model: WeatherPhoto)
    fun getWeather():List<WeatherPhoto>
}