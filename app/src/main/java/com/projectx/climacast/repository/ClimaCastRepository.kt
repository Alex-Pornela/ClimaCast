package com.projectx.climacast.repository

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.util.Log
import com.projectx.climacast.model.UserWeatherObject
import com.projectx.climacast.repository.database.WeatherDatabase
import com.projectx.climacast.tableStructures.UserAccountTable
import com.projectx.climacast.tableStructures.UserWeatherDataTable

class ClimaCastRepository(context: Context) {
    private val db = WeatherDatabase(context).writableDatabase

    fun isUser(userName: String, password: String): Boolean{
        val sql = "SELECT * FROM " + UserAccountTable.TABLE_NAME +
                " WHERE " + UserAccountTable.COLUMN_USERNAME + " =?" +
                " AND " + UserAccountTable.COLUMN_PASSWORD + " =?"
        val selectionArgs = arrayOf(userName, password)

        val cursor: Cursor = db.rawQuery(sql, selectionArgs)
        val userExist = cursor.count > 0
        cursor.close()
        return userExist
    }

    /*@SuppressLint("Range")
    fun getUserWeatherData(): List<UserWeatherObject>{
        val data : ArrayList<UserWeatherObject> = ArrayList()
        val sql = "SELECT * FROM " + UserWeatherDataTable.TABLE_NAME
        val cursor: Cursor = db.rawQuery(sql, null)
        if(cursor.moveToFirst()){
            val locationName = cursor.getString(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_LOCATION_NAME))
            val locationRegion = cursor.getString(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_LOCATION_REGION))
            val time = cursor.getString(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_LOCAL_TIME))
            val temp = cursor.getDouble(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_TEMPERATURE))
            val weatherCondition = cursor.getString(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_WEATHER_CONDITION))
            val weatherIcon = cursor.getString(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_WEATHER_ICON))
            val wind = cursor.getDouble(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_WIND))
            val humidity = cursor.getInt(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_RAIN))
            val cloud = cursor.getInt(cursor.getColumnIndex(UserWeatherDataTable.COLUMN_CLOUD))

            data.add(UserWeatherObject(locationName, locationRegion, time, temp, weatherCondition, weatherIcon, wind, humidity, cloud))
        }
        cursor.close()
        return data
    }*/

}