package com.example.data.db


import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase


import com.example.domain.models.WeatherPhoto


@Database(entities = [WeatherPhoto::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun getWeatherPhotoDao(): WeatherPhotoDao

}