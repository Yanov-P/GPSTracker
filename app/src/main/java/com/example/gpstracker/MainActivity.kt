package com.example.gpstracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    val dbHelper: DBHelper
    var records: Int = 0

    init{
        dbHelper = DBHelper(this)
    }

//    dbHelper.addRow(
//    username.text.toString(),
//    email.text.toString()
//    )


//    val values = dbHelper.getAllValues()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper.clear()
        GPSHelper.getInstance(this)

        btnTest.setOnClickListener {
            runSession()
        }

        button2.setOnClickListener {
//            val values = dbHelper.getAllValues()
//            val toast = Toast.makeText(
//                applicationContext, values.size.toString() + " " +
//                values[0][0] + " " + values[0][1] + " " + values[0][2], Toast.LENGTH_SHORT
//            )
//            toast.show()
        }

    }

    fun runSession(){
        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {

                val location = GPSHelper.imHere
                records++

                dbHelper.addRow(
                    location?.longitude.toString(),
                    location?.latitude.toString(),
                    "ss"
                )
                updateList()

                mainHandler.postDelayed(this, 10000)
            }
        })
    }

    fun updateList(){
        val adapter = MyAdapter(this, DistanceHelper.convertList(dbHelper.getAllValues()))
        listView.adapter = adapter
        val listadp = listView.adapter
        if (listadp != null) {
            var totalHeight = 0

            for (i in 0 until listadp.count) {
                val listItem = listadp.getView(i, null, listView)
                listItem.measure(0, 0)
                totalHeight += listItem.getMeasuredHeight()
            }
            val params = listView.layoutParams
            params.height = totalHeight + (listView.dividerHeight * 4 * (listadp.getCount() + 1))
            listView.setLayoutParams(params)
            listView.requestLayout()
        }
    }
}
