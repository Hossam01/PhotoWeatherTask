package com.example.domain.models



import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weatherPhoto")
data class WeatherPhoto(
    @PrimaryKey
    @NonNull
    var name:String="",
    @ColumnInfo(name = "path")
    var path:String="")
