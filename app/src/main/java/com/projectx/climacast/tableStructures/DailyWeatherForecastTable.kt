package com.projectx.climacast.tableStructures

object DailyWeatherForecastTable {
    const val TABLE_NAME = "daily_weather_forecast"
    const val COLUMN_ID = "id"
    const val COLUMN_LOCATION_REGION = "location_region"
    const val COLUMN_DATE = "date"
    const val COLUMN_TEMPERATURE = "temperature"
    const val COLUMN_WEATHER_CONDITION = "weather_condition"
    const val COLUMN_WEATHER_ICON = "weather_icon"
    const val COLUMN_SUNRISE = "sunrise"
    const val COLUMN_SUNSET = "sunset"

    const val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_LOCATION_REGION + " TEXT," +
            COLUMN_DATE + " TEXT," +
            COLUMN_TEMPERATURE + " DOUBLE," +
            COLUMN_WEATHER_CONDITION + " TEXT," +
            COLUMN_WEATHER_ICON + " TEXT," +
            COLUMN_SUNRISE + " TEXT," +
            COLUMN_SUNSET + " TEXT )"

    const val SQL_INSERT_OR_REPLACE = "INSERT OR REPLACE INTO " + TABLE_NAME + "(" +
            COLUMN_LOCATION_REGION + "," +
            COLUMN_DATE + "," +
            COLUMN_TEMPERATURE + "," +
            COLUMN_WEATHER_CONDITION + "," +
            COLUMN_WEATHER_ICON + "," +
            COLUMN_SUNRISE + "," +
            COLUMN_SUNSET +
            ") VALUES(?,?,?,?,?,?,?)"

    const val CREATE_UNIQUE_INDEX = "CREATE UNIQUE INDEX IF NOT EXISTS " + TABLE_NAME + "_INDEX ON " + TABLE_NAME + "(" + COLUMN_ID + ")"
}

