package com.projectx.climacast

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.projectx.climacast.databinding.ActivityLoginBinding
import com.projectx.climacast.repository.database.WeatherDatabase
import com.projectx.climacast.viewModel.LoginViewModel
import com.projectx.climacast.viewModel.UserWeatherViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        binding.signInBtn.setOnClickListener{
            val userName = binding.etUsename.text.toString().replace("\\s+".toRegex(), "")
            val password = binding.etPassword.text.toString().replace("\\s+".toRegex(), "")
            viewModel.signIn(userName, password).observe(this, Observer { isSuccess ->
                if(isSuccess){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                }
            })

        }

    }
}