package com.example.data.response

import com.example.domain.models.WeatherModel

data class WeatherResponse(
    val current: Current? = null,
    val location: Location? = null
)
{
    fun toWeatherModel()=WeatherModel(
        code=current!!.condition.code,
        icon=current.condition.icon,
        text=current.condition.text,
        cloud = current!!.cloud,
        feelslike_c = current.feelslike_c,
        feelslike_f = current.feelslike_f,
        gust_kph = current.gust_kph,
        gust_mph = current.gust_mph,
        humidity = current.humidity,
        is_day = current.is_day,
        last_updated = current.last_updated,
        last_updated_epoch = current.last_updated_epoch,
        precip_in = current.precip_in,
        precip_mm = current.precip_mm,
        pressure_in = current.pressure_in,
        pressure_mb = current.pressure_mb,
        temp_c = current.temp_c,
        temp_f = current.temp_f,
        uv = current.uv,
        vis_km = current.vis_km,
        vis_miles = current.vis_miles,
        wind_degree = current.wind_degree,
        wind_dir = current.wind_dir,
        wind_kph = current.wind_kph,
        wind_mph = current.wind_mph,
        country = location!!.country,
        lat = location!!.lat,
        localtime = location.localtime,
        localtime_epoch = location.localtime_epoch,
        lon = location.lon,
        name = location.name,
        region = location.region,
        tz_id = location.tz_id,
    )
}

data class Condition(
    val code: Int,
    val icon: String,
    val text: String
)

data class Current(
    val cloud: Int,
    val condition: Condition,
    val feelslike_c: Double,
    val feelslike_f: Double,
    val gust_kph: Double,
    val gust_mph: Double,
    val humidity: Int,
    val is_day: Int,
    val last_updated: String,
    val last_updated_epoch: Int,
    val precip_in: Double,
    val precip_mm: Double,
    val pressure_in: Double,
    val pressure_mb: Double,
    val temp_c: Double,
    val temp_f: Double,
    val uv: Double,
    val vis_km: Double,
    val vis_miles: Double,
    val wind_degree: Int,
    val wind_dir: String,
    val wind_kph: Double,
    val wind_mph: Double
)

data class Location(
    val country: String,
    val lat: Double,
    val localtime: String,
    val localtime_epoch: Int,
    val lon: Double,
    val name: String,
    val region: String,
    val tz_id: String
)
