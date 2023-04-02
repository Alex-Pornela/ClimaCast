package com.projectx.climacast.tableStructures

object UserWeatherDataTable {
    const val TABLE_NAME = "user_weather_data"
    const val COLUMN_ID = "id"
    const val COLUMN_LOCATION_NAME = "location_name"
    const val COLUMN_LOCATION_REGION = "location_region"
    const val COLUMN_LOCAL_TIME = "local_time"
    const val COLUMN_TEMPERATURE = "temperature"
    const val COLUMN_WEATHER_CONDITION = "weather_condition"
    const val COLUMN_WEATHER_ICON = "weather_icon"
    const val COLUMN_WIND = "wind"
    const val COLUMN_RAIN = "humidity"
    const val COLUMN_CLOUD = "cloud"

    const val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_LOCATION_NAME + " TEXT," +
            COLUMN_LOCATION_REGION + " TEXT," +
            COLUMN_LOCAL_TIME + " TEXT," +
            COLUMN_TEMPERATURE + " DOUBLE," +
            COLUMN_WEATHER_CONDITION + " TEXT," +
            COLUMN_WEATHER_ICON + " TEXT," +
            COLUMN_WIND + " INTEGER," +
            COLUMN_RAIN + " INTEGER," +
            COLUMN_CLOUD + " INTEGER )"

    const val SQL_INSERT_OR_REPLACE = "INSERT OR REPLACE INTO " + TABLE_NAME + "(" +
            COLUMN_LOCATION_NAME + "," +
            COLUMN_LOCATION_REGION + "," +
            COLUMN_LOCAL_TIME + "," +
            COLUMN_TEMPERATURE + "," +
            COLUMN_WEATHER_CONDITION + "," +
            COLUMN_WEATHER_ICON + "," +
            COLUMN_WIND + "," +
            COLUMN_RAIN + "," +
            COLUMN_CLOUD +
            ") VALUES(?,?,?,?,?,?,?,?,?)"

    const val CREATE_UNIQUE_INDEX =
        "CREATE UNIQUE INDEX IF NOT EXISTS " + TABLE_NAME + "_INDEX ON " + TABLE_NAME + "(" + COLUMN_ID + ")"
}