package com.example.domain.usecase.weather

import com.example.domain.constants.DataState
import com.example.domain.dataInterface.RemoteRepository
import com.example.domain.models.WeatherModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeatherUseCase(private val repository: RemoteRepository) {
    operator fun invoke(): Flow<DataState<WeatherModel>> =
        flow {
            try {
                emit(DataState.Loading())
                val pageDetail = repository.weather()
                emit(DataState.Success(pageDetail))
            } catch (e: Exception) {

                emit(DataState.Error(e.message.toString()))

            }
        }
}