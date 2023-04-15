package com.example.domain.dataInterface

import com.example.domain.models.WeatherModel

interface RemoteRepository {
    suspend fun weather(): WeatherModel
}