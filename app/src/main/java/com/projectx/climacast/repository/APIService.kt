package com.projectx.climacast.repository

import com.projectx.climacast.model.UserWeatherModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService {

    @GET
    fun getCurrentWeather(@Url url: String): Call<UserWeatherModel>

}