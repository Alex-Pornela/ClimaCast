package com.projectx.climacast.model

data class UserWeatherObject(
    val locationName: String,
    val locationRegion: String,
    val time: String,
    val temp: Double,
    val weatherCondition: String,
    val weatherIcon: String,
    val wind: Double,
    val humidity : Int,
    val cloud: Int,
    val dailyForecast: List<DailyForecastModel>
)
