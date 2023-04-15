package com.example.photoweathertask.NewPhoto

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.constants.DataState
import com.example.domain.models.WeatherPhoto
import com.example.domain.usecase.weather.SaveWeatherUseCase
import com.example.domain.usecase.weather.WeatherUseCase
import com.example.photoweathertask.NewPhoto.view_state.NewPhotoIntents
import com.example.photoweathertask.NewPhoto.view_state.NewPhotoUiState
import com.example.photoweathertask.base.ui.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class NewPhotoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val weatherUsCase: WeatherUseCase,
    private val saveWeatherUseCase:SaveWeatherUseCase
) : ViewModel() {

    var pathData = SingleLiveEvent<String>()
    val _uiState = SingleLiveEvent<NewPhotoUiState>()
    private var job: Job? = null

    init {
        savedStateHandle.get<String>("path")?.let { path ->
            if (path != null) {
                pathData.postValue(path)
            }
        }
    }

    fun onEvent(event: NewPhotoIntents){
        when (event) {
            is NewPhotoIntents.NewPhotoIntent -> {
                loadWeatherData()
            }
            is NewPhotoIntents.SavePhotoIntent->
            {
                saveWeather(event.name,event.path)
            }
            is NewPhotoIntents.Refresh->{
            }
        }
    }


    private fun loadWeatherData() {
        job?.cancel()
        job = weatherUsCase()
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _uiState.postValue(NewPhotoUiState(isLoading = true))
                    }
                    is DataState.Success -> {
                        _uiState.postValue(NewPhotoUiState(isLoading = false))
                        dataState.data?.let { movieDetail ->
                            _uiState.postValue(NewPhotoUiState(weatherModel = movieDetail))
                        }
                    }
                    is DataState.Error -> {
                        _uiState.postValue(
                            NewPhotoUiState(
                                isLoading = false,
                                errorMessage = dataState.message
                            )
                        )
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }


    private fun saveWeather(name:String,path:String)
    {
        saveWeatherUseCase(WeatherPhoto(name, path))
    }
}