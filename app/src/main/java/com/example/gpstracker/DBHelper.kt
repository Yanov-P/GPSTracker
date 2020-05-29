package com.example.gpstracker

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "myDB", null, 1)
{

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE test (" +
                "id integer primary key autoincrement," +
                "longtitude text not null," +
                "latitude text not null," +
                "timestamp text not null" +
                ")")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    fun clear(){
        val db = writableDatabase
        db?.execSQL("delete from test")
    }

    fun addRow(name: String, email: String, timestamp: String) {
        val values = ContentValues()
        values.put("longtitude", name)
        values.put("latitude", email)
        values.put("timestamp", timestamp)

        val db = writableDatabase
        db.insert("test", null, values)
        db.close()
    }

    fun getAllValues(): List<Array<String>>{
        val db = readableDatabase
        val cursor = db.query("test", null, null, null, null, null, "id")
        val result = mutableListOf<Array<String>>()
        if (cursor.count>0){
            while (cursor.moveToNext()){
                val row = Array<String>( 3) {""}
                row[0] = cursor.getString(cursor.getColumnIndex("longtitude"))
                row[1] = cursor.getString(cursor.getColumnIndex("latitude"))
                row[2] = cursor.getString(cursor.getColumnIndex("timestamp"))

                result.add(row)
            }
        }
        cursor.close()
        return result

    }
}