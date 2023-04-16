package com.example.domain.usecase.weather

import com.example.domain.constants.DataState
import com.example.domain.dataInterface.LocalRepository
import com.example.domain.models.WeatherModel
import com.example.domain.models.WeatherPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoadWeatherUseCase(private val repository: LocalRepository) {


    operator fun invoke(): Flow<DataState<List<WeatherPhoto>?>> =
        flow {
            try {
                emit(DataState.Loading())
                val pageDetail = repository.getWeather()
                emit(DataState.Success(pageDetail))
            } catch (e: Exception) {

                emit(DataState.Error(e.message.toString()))

            }
        }
}