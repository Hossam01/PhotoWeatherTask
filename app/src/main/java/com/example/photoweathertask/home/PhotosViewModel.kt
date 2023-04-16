package com.example.photoweathertask.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.constants.DataState
import com.example.domain.usecase.weather.LoadWeatherUseCase
import com.example.photoweathertask.NewPhoto.view_state.LoadPhotoIntents
import com.example.photoweathertask.NewPhoto.view_state.LoadPhotoUiState
import com.example.photoweathertask.base.ui.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val loadWeatherUseCase: LoadWeatherUseCase
):ViewModel() {
    val _uiState = SingleLiveEvent<LoadPhotoUiState>()

    private var job: Job? = null

    fun onEvent(event: LoadPhotoIntents){
        when (event) {
            is LoadPhotoIntents.LoadPhotoIntent -> {
                loadWeatherData()
            }

            is LoadPhotoIntents.Refresh->{
            }
            else -> {}
        }
    }

    private fun loadWeatherData() {
        job?.cancel()
        job = loadWeatherUseCase()
            .onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        _uiState.postValue(LoadPhotoUiState(isLoading = true))
                    }
                    is DataState.Success -> {
                        _uiState.postValue(LoadPhotoUiState(isLoading = false))
                        dataState.data?.let { movieDetail ->
                            _uiState.postValue(LoadPhotoUiState(weatherModel = movieDetail))
                        }
                    }
                    is DataState.Error -> {
                        _uiState.postValue(
                            LoadPhotoUiState(
                                isLoading = false,
                                errorMessage = dataState.message
                            )
                        )
                    }
                    else -> {}
                }
            }.launchIn(viewModelScope)
    }

}