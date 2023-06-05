package com.example.nevigaionmaneger

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    companion object {
        private const val DATABASE_NAME = "model.db"
        private const val DATABASE_VERSION = 1

        // Define the table and column names
        private const val TABLE_MODEL = "model"
        private const val COLUMN_ID = "id"
        private const val COLUMN_CITY = "city"
        private const val COLUMN_STATE = "state"
        private const val COLUMN_COUNTRY = "country"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the table
        val createTableQuery = "CREATE TABLE $TABLE_MODEL ($COLUMN_ID TEXT PRIMARY KEY, $COLUMN_CITY TEXT, $COLUMN_STATE TEXT, $COLUMN_COUNTRY TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop the existing table if it exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MODEL")
        onCreate(db)
    }

    // Insert a values into the database
    fun insertModel(model: Model) {
        val db = writableDatabase
        val values = ContentValues()
        values.put(COLUMN_ID, model.id)
        values.put(COLUMN_CITY, model.city)
        values.put(COLUMN_STATE, model.state)
        values.put(COLUMN_COUNTRY, model.country)
        db.insert(TABLE_MODEL, null, values)
        db.close()
    }

    // Delete model form db
    fun deleteModel(modelId: String) {
        val db = writableDatabase
        db.delete(TABLE_MODEL, "$COLUMN_ID=?", arrayOf(modelId))
        db.close()
    }

    // Get all values from the database
    @SuppressLint("Range")
    fun getAllModel(): ArrayList<Model> {
        val modelList = ArrayList<Model>()
        val db = readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_MODEL", null)

        if (cursor?.moveToFirst() == true) {
            do {
                val id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                val city = cursor.getString(cursor.getColumnIndex(COLUMN_CITY))
                val state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE))
                val country = cursor.getString(cursor.getColumnIndex(COLUMN_COUNTRY))

                val model = Model(id,  city, state, country)
                modelList.add(model)
            } while (cursor.moveToNext())
        }

        cursor?.close()
        db.close()

        return modelList
    }
}
