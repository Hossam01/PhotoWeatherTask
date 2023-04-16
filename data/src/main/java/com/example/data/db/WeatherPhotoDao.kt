package com.example.data.db




import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.domain.models.WeatherPhoto




@Dao
interface WeatherPhotoDao {

    @Insert(onConflict = REPLACE)
    fun insert(weatherPhoto: WeatherPhoto)

    @Query("SELECT * FROM weatherPhoto")
    fun getWeatherPhotoList(): List<WeatherPhoto>
}