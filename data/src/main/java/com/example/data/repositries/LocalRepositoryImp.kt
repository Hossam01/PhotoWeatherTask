package com.example.data.repositries

import androidx.lifecycle.LiveData
import com.example.data.db.WeatherPhotoDao
import com.example.domain.dataInterface.LocalRepository
import com.example.domain.models.WeatherModel
import com.example.domain.models.WeatherPhoto

class LocalRepositoryImp (
   private val weatherDao: WeatherPhotoDao
        ): LocalRepository {

    override fun setAddWeather(model: WeatherPhoto) {
        weatherDao.insert(model)
    }

    override fun getWeather(): List<WeatherPhoto> {
       return weatherDao.getWeatherPhotoList()
    }
}