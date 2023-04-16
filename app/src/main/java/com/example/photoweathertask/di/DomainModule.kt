package com.example.photoweathertask.di

import com.example.domain.dataInterface.LocalRepository
import com.example.domain.dataInterface.RemoteRepository
import com.example.domain.usecase.weather.LoadWeatherUseCase
import com.example.domain.usecase.weather.SaveWeatherUseCase
import com.example.domain.usecase.weather.WeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun getWeatherUseCase(repository: RemoteRepository): WeatherUseCase = WeatherUseCase(repository)

    @Provides
    @Singleton
    fun saveWeatherUseCase(repository: LocalRepository): SaveWeatherUseCase = SaveWeatherUseCase(repository)

    @Provides
    @Singleton
    fun loadWeatherUseCase(repository: LocalRepository): LoadWeatherUseCase =
        LoadWeatherUseCase(repository)



}