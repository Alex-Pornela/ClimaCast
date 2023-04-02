package com.projectx.climacast.repository

import android.content.Context
import android.database.sqlite.SQLiteStatement
import android.location.Location
import android.util.Log
import com.projectx.climacast.model.DailyForecastModel
import com.projectx.climacast.model.UserWeatherModel
import com.projectx.climacast.model.UserWeatherObject
import com.projectx.climacast.repository.database.WeatherDatabase
import com.projectx.climacast.tableStructures.DailyWeatherForecastTable
import com.projectx.climacast.tableStructures.UserWeatherDataTable
import com.projectx.climacast.viewModel.UserWeatherViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherForecastDownload(context: Context) {
    private var apiClient: APIClient = APIClient.getInstance()
    private var db = WeatherDatabase(context).writableDatabase
    private lateinit var dataInsertStatement: SQLiteStatement
    private lateinit var forecastInsertStatement: SQLiteStatement

    fun downloadWeather(location: Location, listener: UserWeatherViewModel.OnForecastDataDownloaded):  List<UserWeatherObject>{
        val weatherData = mutableListOf<UserWeatherObject>()
        dataInsertStatement = db.compileStatement(UserWeatherDataTable.SQL_INSERT_OR_REPLACE)
        forecastInsertStatement = db.compileStatement(DailyWeatherForecastTable.SQL_INSERT_OR_REPLACE)
        apiClient.getCurrentWeather(location, object : Callback<UserWeatherModel>{
            override fun onResponse(call: Call<UserWeatherModel>, response: Response<UserWeatherModel>) {
                val userWeatherModel = response.body()
                if (userWeatherModel != null) {
                  /* dataInsertStatement.bindString(1, userWeatherModel.location.name)
                   dataInsertStatement.bindString(2, userWeatherModel.location.region)
                   dataInsertStatement.bindString(3, userWeatherModel.location.localtime)
                   dataInsertStatement.bindDouble(4, userWeatherModel.current.tempCelsius.toDouble())
                   dataInsertStatement.bindString(5, userWeatherModel.current.condition.text)
                   dataInsertStatement.bindString(6, userWeatherModel.current.condition.iconUrl)
                   dataInsertStatement.bindDouble(7, userWeatherModel.current.windMph.toDouble())
                   dataInsertStatement.bindDouble(8, userWeatherModel.current.humidity.toDouble())
                   dataInsertStatement.bindDouble(9, userWeatherModel.current.cloud.toDouble())
                   dataInsertStatement.executeInsert()

                    forecastInsertStatement.bindString(1, userWeatherModel.location.region)
                    userWeatherModel.forecast.forecastDayList.forEach{
                        forecastInsertStatement.bindString(2, it.date)
                        forecastInsertStatement.bindDouble(3, it.day.maxTempCelsius.toDouble())
                        forecastInsertStatement.bindString(4, it.day.condition.text)
                        forecastInsertStatement.bindString(5, it.day.condition.iconUrl)
                        forecastInsertStatement.bindString(6, it.astro.sunrise)
                        forecastInsertStatement.bindString(7, it.astro.sunset)
                        forecastInsertStatement.executeInsert()
                    }*/
                    val locationName = userWeatherModel.location.name
                    val locationRegion = userWeatherModel.location.region
                    val time =  userWeatherModel.location.localtime
                    val temp = userWeatherModel.current.tempCelsius.toDouble()
                    val weatherCondition = userWeatherModel.current.condition.text
                    val weatherIcon = userWeatherModel.current.condition.iconUrl
                    val wind = userWeatherModel.current.windMph.toDouble()
                    val humidity = userWeatherModel.current.humidity
                    val cloud = userWeatherModel.current.cloud

                    val dailyForecastList: ArrayList<DailyForecastModel> = ArrayList()
                    userWeatherModel.forecast.forecastDayList.forEach{
                        val date = it.date
                        val temperature = it.day.maxTempCelsius.toDouble()
                        val condition = it.day.condition.text
                        val conditionIcon = it.day.condition.iconUrl
                        val sunrise = it.astro.sunrise
                        val sunset = it.astro.sunset
                        dailyForecastList.add(DailyForecastModel(date,temperature,condition, conditionIcon, sunrise, sunset))
                    }
                    weatherData.add(UserWeatherObject(locationName, locationRegion, time, temp, weatherCondition, weatherIcon, wind, humidity, cloud, dailyForecastList))
                    listener.onFinished()

                }
            }

            override fun onFailure(call: Call<UserWeatherModel>, t: Throwable) {
                listener.onFail()
                Log.d("Request", "Failed")
            }

        })
        return weatherData
    }

}