package com.paperatus.unilife

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

object Courses {

    var favourites = ArrayList<CourseData>()

    lateinit var names: Array<String>
    var data = HashMap<String, Array<CourseData>>()

    fun favourite(context: Context, data: CourseData) {
        val isIn = data in favourites
        context.getSharedPreferences("data", Context.MODE_PRIVATE).edit().putBoolean(data.toString(), !isIn).apply()
        if (isIn) {
            favourites.remove(data)
        } else {
            favourites.add(data)
        }
    }

    fun read(context: Context) {
        context.resources.assets.open("courses.txt").bufferedReader().use { reader ->
            var courseName = reader.readLine()
            while(courseName != null) {
                val count = reader.readLine().toInt()

                val arr = Array<CourseData>(count) {
                    var number = reader.readLine().toInt()
                    var nameDesc = reader.readLine()
                    var desc = reader.readLine()
                    CourseData(courseName, number, nameDesc, desc)
                }

                data[courseName] = arr

                courseName = reader.readLine()
            }
        }


        val sorted = data.keys.sorted().filterNot { it.isEmpty() }
        names = Array(sorted.size) {
            sorted[it]
        }

        data.values.forEach {
            it.forEach {
                if (context.getSharedPreferences("data", Context.MODE_PRIVATE).getBoolean(it.toString(), false)) {
                    favourites.add(it)
                }
            }
        }
    }
}

data class CourseData(val name: String, val number: Int, val summary: String, val desc: String)