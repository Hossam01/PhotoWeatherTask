package com.example.data.repositries

import com.example.data.remote.ApiService
import com.example.domain.dataInterface.RemoteRepository
import com.example.domain.models.WeatherModel

class RemoteRepositoryImp(
    private val apiService: ApiService,
) : RemoteRepository {
    override suspend fun weather(): WeatherModel {
        return apiService.getWeather().toWeatherModel()
    }
}