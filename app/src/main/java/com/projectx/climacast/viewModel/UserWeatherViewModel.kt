package com.projectx.climacast.viewModel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projectx.climacast.model.UserWeatherModel
import com.projectx.climacast.model.UserWeatherObject
import com.projectx.climacast.repository.ClimaCastRepository
import com.projectx.climacast.repository.WeatherForecastDownload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserWeatherViewModel(application: Application): AndroidViewModel(application) {

    private val repository =  ClimaCastRepository(application)
    private var weatherData: MutableLiveData<List<UserWeatherObject>> = MutableLiveData()
    private val download = WeatherForecastDownload(application)

    fun getCurrentWeather(location: Location, listener: OnForecastDataDownloaded) {
        CoroutineScope(Dispatchers.IO).launch {
            weatherData.postValue(download.downloadWeather(location, listener))
        }
    }

    /*fun getUserWeatherData(){
        CoroutineScope(Dispatchers.IO).launch {
            weatherData.postValue(repository.getUserWeatherData())
        }
    }*/

    fun getWeatherLiveData(): LiveData<List<UserWeatherObject>> {
        return weatherData
    }

    interface OnForecastDataDownloaded {
        fun onFinished()
        fun onFail()
    }
}