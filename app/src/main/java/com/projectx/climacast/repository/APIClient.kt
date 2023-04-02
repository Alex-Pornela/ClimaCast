package com.projectx.climacast.repository

import android.location.Location
import android.net.Uri
import android.util.Log
import com.projectx.climacast.model.UserWeatherModel
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        private const val BASE_URL = "https://api.weatherapi.com/v1/"
        private const val API_KEY = "9764970c4d2540c981262548230104"
        private const val DAYS = "7"
        private var instance: APIClient? = null

        @Synchronized
        fun getInstance(): APIClient {
            if (instance == null) {
                instance = APIClient()
            }
            return instance!!
        }
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getCurrentWeather(location: Location, callback: Callback<UserWeatherModel>) {
        val api: APIService = retrofit.create(APIService::class.java)
        val latLngString = "${location.latitude},${location.longitude}"
        val url = "${BASE_URL}forecast.json?key=$API_KEY&q=$latLngString&days=$DAYS"
        val call: Call<UserWeatherModel> = api.getCurrentWeather(url)
        call.enqueue(callback)
    }


}