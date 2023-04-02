package com.projectx.climacast.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projectx.climacast.databinding.DailyForecastLayoutBinding
import com.projectx.climacast.model.UserWeatherModel
import com.projectx.climacast.model.UserWeatherObject
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DailyForecastAdapter : ListAdapter<UserWeatherObject, DailyForecastAdapter.MyViewHolder>(DiffCallback()) {

    inner class MyViewHolder(val binding: DailyForecastLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DailyForecastLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)

        holder.binding.container.removeAllViews()
        // Inflate a new view for each daily forecast and add it to the holder
        item.dailyForecast.forEach { forecast ->
            val dailyForecastBinding = DailyForecastLayoutBinding.inflate(LayoutInflater.from(holder.itemView.context), holder.binding.container, true)
            dailyForecastBinding.day.text = formatDate(forecast.date)
            dailyForecastBinding.sunrise.text = forecast.sunrise
            dailyForecastBinding.sunset.text = forecast.sunset
            dailyForecastBinding.temperature.text = "${forecast.temperature.toInt()}%"
            val url = forecast.conditionIcon
            Picasso.get().load("https:$url").into(dailyForecastBinding.condition)
            Log.d("LISTDATA", forecast.toString())
        }

    }


    class DiffCallback : DiffUtil.ItemCallback<UserWeatherObject>(){
        override fun areItemsTheSame(oldItem: UserWeatherObject, newItem: UserWeatherObject): Boolean {
            if (oldItem.dailyForecast.size != newItem.dailyForecast.size) {
                return false
            }

            return oldItem.dailyForecast.all { oldForecast ->
                newItem.dailyForecast.any { newForecast ->
                    oldForecast.date == newForecast.date
                }
            }
        }
        override fun areContentsTheSame(oldItem: UserWeatherObject, newItem: UserWeatherObject): Boolean {
            return  oldItem == newItem
        }

    }

    private fun formatDate(dateString: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = dateFormat.parse(dateString)

        val cal = Calendar.getInstance()
        cal.time = date

        return cal.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
    }


}

