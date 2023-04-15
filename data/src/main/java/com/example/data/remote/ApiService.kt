package com.example.data.remote

import com.example.data.response.WeatherResponse
import com.example.data.util.EndPoints
import retrofit2.http.*

interface ApiService {

    @GET(EndPoints.weather)
    suspend fun getWeather(): WeatherResponse
}
