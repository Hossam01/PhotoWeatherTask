package com.example.photoweathertask.NewPhoto.view_state

import com.example.domain.models.WeatherPhoto

data class LoadPhotoUiState(
    val isLoading: Boolean = false,
    val weatherModel: List<WeatherPhoto> ?= null,
    val errorMessage: String? = null
)

sealed class LoadPhotoIntents {
    object LoadPhotoIntent : LoadPhotoIntents()
    object Refresh: LoadPhotoIntents()
}