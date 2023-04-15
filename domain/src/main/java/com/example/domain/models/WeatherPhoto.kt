package com.example.domain.models

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherPhoto")
data class WeatherPhoto(
    @PrimaryKey
    @NonNull
    val name:String,
    val path:String)
