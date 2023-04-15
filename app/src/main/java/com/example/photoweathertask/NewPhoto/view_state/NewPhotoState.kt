package com.example.photoweathertask.NewPhoto.view_state

import com.example.domain.models.WeatherModel

data class NewPhotoUiState(
    val isLoading: Boolean = false,
    val weatherModel: WeatherModel ?= null,
    val errorMessage: String? = null
)

sealed class NewPhotoIntents {
    object NewPhotoIntent : NewPhotoIntents()
    data class SavePhotoIntent(val name: String,val path: String) : NewPhotoIntents()
    object Refresh: NewPhotoIntents()
}