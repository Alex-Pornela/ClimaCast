package com.projectx.climacast.repository.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.projectx.climacast.tableStructures.DailyWeatherForecastTable
import com.projectx.climacast.tableStructures.UserAccountTable
import com.projectx.climacast.tableStructures.UserWeatherDataTable


class WeatherDatabase(context: Context): SQLiteOpenHelper(context, "ClimaCast.sqlite", null, 1) {


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(UserWeatherDataTable.SQL_CREATE_TABLE)
        db.execSQL(UserWeatherDataTable.CREATE_UNIQUE_INDEX)

        db.execSQL(UserAccountTable.SQL_CREATE_TABLE)
        db.execSQL(UserAccountTable.CREATE_UNIQUE_INDEX)

        val defaultUsername = "User1"
        val defaultPassword = "Password123"
        val contentValues = ContentValues().apply {
            put(UserAccountTable.COLUMN_USERNAME, defaultUsername)
            put(UserAccountTable.COLUMN_PASSWORD, defaultPassword)
        }
        db.insert(UserAccountTable.TABLE_NAME, null, contentValues)

        db.execSQL(DailyWeatherForecastTable.SQL_CREATE_TABLE)
        db.execSQL(DailyWeatherForecastTable.CREATE_UNIQUE_INDEX)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}
