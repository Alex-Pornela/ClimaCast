package com.projectx.climacast.model

import com.google.gson.annotations.SerializedName

data class UserWeatherModel(
    @SerializedName("current")
    val current: CurrentWeather,

    @SerializedName("location")
    val location: WeatherLocation,

    @SerializedName("forecast")
    val forecast: Forecast
)

data class WeatherLocation(
    @SerializedName("name")
    val name: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("localtime")
    val localtime: String
)

data class CurrentWeather(
    @SerializedName("last_updated")
    val lastUpdated: String,

    @SerializedName("temp_c")
    val tempCelsius: Float,

    @SerializedName("condition")
    val condition: WeatherCondition,

    @SerializedName("wind_mph")
    val windMph: Float,

    @SerializedName("humidity")
    val humidity: Int,

    @SerializedName("cloud")
    val cloud: Int
)

data class WeatherCondition(
    @SerializedName("text")
    val text: String,

    @SerializedName("icon")
    val iconUrl: String
)

data class Forecast(
    @SerializedName("forecastday")
    val forecastDayList: List<ForecastDay>
)

data class ForecastDay(
    @SerializedName("date")
    val date: String,

    @SerializedName("day")
    val day: Day,

    @SerializedName("astro")
    val astro: Astro
)

data class Day(
    @SerializedName("maxtemp_c")
    val maxTempCelsius: Float,

    @SerializedName("maxwind_mph")
    val maxWindMph: Float,

    @SerializedName("totalsnow_cm")
    val totalSnowCm: Float,

    @SerializedName("daily_will_it_rain")
    val dailyWillItRain: Int,

    @SerializedName("condition")
    val condition: WeatherCondition
)

data class Astro(
    @SerializedName("sunrise")
    val sunrise: String,

    @SerializedName("sunset")
    val sunset: String,

    @SerializedName("is_moon_up")
    val isMoonUp: Int,

    @SerializedName("is_sun_up")
    val isSunUp: Int
)
