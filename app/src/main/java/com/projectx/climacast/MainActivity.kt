package com.projectx.climacast

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.projectx.climacast.adapter.DailyForecastAdapter
import com.projectx.climacast.databinding.ActivityMainBinding
import com.projectx.climacast.databinding.LocationDialogBinding
import com.projectx.climacast.viewModel.UserWeatherViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val REQUEST_LOCATION_PERMISSION = 100
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var  viewModel: UserWeatherViewModel
    private val forecastAdapter =  DailyForecastAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationIsEnabled()
        viewModel = ViewModelProvider(this)[UserWeatherViewModel::class.java]
        showProgressBar(true)
        binding.apply {
            recyclerView.apply {
                adapter = forecastAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    private fun checkLocationIsEnabled() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            val binding = LocationDialogBinding.inflate(layoutInflater)
            val builder = AlertDialog.Builder(this)
            builder.setView(binding.root)
            val dialog = builder.create()
            binding.btnSettings.setOnClickListener {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
                dialog.dismiss()
            }
            binding.btnCancel.setOnClickListener {
                dialog.dismiss()
            }

            dialog.show()
        } else {
            requestLocationPermissions()
        }

    }

    private fun requestLocationPermissions() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), REQUEST_LOCATION_PERMISSION)
        } else {
            getUserLocation()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getUserLocation()
            }
        }
    }

    /*override fun onResume() {
        super.onResume()
        getUserLocation()
    }*/

    private fun getUserLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let { it ->
                viewModel.getCurrentWeather(it, object: UserWeatherViewModel.OnForecastDataDownloaded{
                    override fun onFinished() {
                        loadData()
                    }

                    override fun onFail() {
                        runOnUiThread{
                            Toast.makeText(this@MainActivity,"Failed getting data", Toast.LENGTH_SHORT).show()
                        }
                    }

                })
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun loadData() {
        runOnUiThread {
            viewModel.getWeatherLiveData().observe(this@MainActivity){
                forecastAdapter.submitList(it)
                it.forEach { data ->
                    binding.location.text = "${data.locationName}, ${data.locationRegion}"
                    binding.date.text = formatDate(data.time)
                    binding.degrees.text = "${data.temp}°"
                    binding.condition.text = data.weatherCondition
                    val url = data.weatherIcon
                    Picasso.get().load("https:$url").into(binding.imageCloud)
                    binding.wind.text = "${data.wind.toInt()} m/s"
                    binding.humidity.text = "${data.humidity} %"
                    binding.rain.text = "${data.cloud} %"

                    showProgressBar(false)
                }
            }
        }
    }

    private fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val outputFormat = SimpleDateFormat("d MMMM, EEEE", Locale.getDefault())
        val date = inputFormat.parse(dateString)
        return outputFormat.format(date)
    }

    private fun showProgressBar(isRunning: Boolean){
        binding.container.visibility = if(isRunning) View.GONE else View.VISIBLE
        binding.progressBar.visibility = if(isRunning) View.VISIBLE else View.GONE

    }
}