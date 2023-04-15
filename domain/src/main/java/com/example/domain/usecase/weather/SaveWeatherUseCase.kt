package com.example.domain.usecase.weather

import com.example.domain.dataInterface.LocalRepository
import com.example.domain.models.WeatherModel
import com.example.domain.models.WeatherPhoto

class SaveWeatherUseCase(private val repository: LocalRepository) {
    operator fun invoke(weatherModel: WeatherPhoto){
        repository.setAddWeather(weatherModel)
    }
}