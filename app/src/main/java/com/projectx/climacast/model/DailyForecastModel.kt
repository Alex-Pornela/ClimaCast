package com.projectx.climacast.model

data class DailyForecastModel(
    val date: String,
    val temperature: Double,
    val condition: String,
    val conditionIcon: String,
    val sunrise: String,
    val sunset: String
)
