package com.example.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.example.domain.models.WeatherPhoto


@Database(entities = [WeatherPhoto::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {


    companion object {
        private val DATA_BASE_NAME = "weather_db"
        private var INSTANCE: WeatherDataBase? = null

        @Synchronized
        fun getDatabaseInstance(context: Context): WeatherDataBase? {
            if (INSTANCE == null) {
                INSTANCE = databaseBuilder(
                    context.getApplicationContext(),
                    WeatherDataBase::class.java, DATA_BASE_NAME
                ).fallbackToDestructiveMigration().build()
            }
            return INSTANCE
        }
    }

    override fun clearAllTables() {
        TODO("Not yet implemented")
    }

    override fun createInvalidationTracker(): InvalidationTracker {
        TODO("Not yet implemented")
    }

    override fun createOpenHelper(config: DatabaseConfiguration): SupportSQLiteOpenHelper {
        TODO("Not yet implemented")
    }

    abstract fun getWeatherPhotoDao(): WeatherPhotoDao

}