package com.example.gpstracker

import java.util.*

class DistanceHelper private constructor(){

    companion object {

        fun convertList(arr: List<Array<String>>): List<Array<String>> {

            fun deg2rad(deg: Double): Double {
                return deg * Math.PI / 180.0
            }

            fun rad2deg(rad: Double): Double {
                return rad * 180.0 / Math.PI
            }

            fun distance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
                val theta = lon1 - lon2
                var dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + (Math.cos(deg2rad(lat1))
                        * Math.cos(deg2rad(lat2))
                        * Math.cos(deg2rad(theta)))
                dist = Math.acos(dist)
                dist = rad2deg(dist)
                dist = dist * 60.0 * 1.1515
                return dist
            }

            var res = List(arr.size) { Array(3) { "0" } }

            for (i in 1 until arr.size) {
                res[i][0] = i.toString()
                val dist = distance(
                    arr[i][1].toDouble(),
                    arr[i][0].toDouble(),
                    arr[i - 1][1].toDouble(),
                    arr[i - 1][0].toDouble()
                )
                res[i][1] = String.format(Locale.ROOT,"%.2f",(dist * 1000))
                res[i][2] = String.format(Locale.ROOT,"%.2f",(dist*1000 + res[i - 1][2].toDouble()))
            }

            return res
        }
    }



}