package com.projectx.climacast.tableStructures

object UserAccountTable {
    const val TABLE_NAME = "user_account"
    const val COLUMN_ID = "id"
    const val COLUMN_USERNAME = "username"
    const val COLUMN_PASSWORD = "password"

    const val SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_USERNAME + " TEXT," +
            COLUMN_PASSWORD + " TEXT )"

    const val SQL_INSERT_OR_REPLACE = "INSERT OR REPLACE INTO " + TABLE_NAME + "(" +
            COLUMN_USERNAME + "," +
            COLUMN_PASSWORD +
            ") VALUES(?,?)"

    const val CREATE_UNIQUE_INDEX =
        "CREATE UNIQUE INDEX IF NOT EXISTS " + TABLE_NAME + "_INDEX ON " + TABLE_NAME + "(" + COLUMN_ID + ")"

}