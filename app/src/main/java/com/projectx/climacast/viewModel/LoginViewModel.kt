package com.projectx.climacast.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.projectx.climacast.repository.ClimaCastRepository
import kotlinx.coroutines.*

class LoginViewModel(application: Application): AndroidViewModel(application) {
    private val repository =  ClimaCastRepository(application)

    fun signIn(username: String, password:String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        CoroutineScope(Dispatchers.IO).launch {
            val userExists = repository.isUser(username, password)
            result.postValue(userExists)
        }
        return result
    }
}