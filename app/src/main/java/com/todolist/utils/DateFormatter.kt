package com.todolist.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateFormatter {
    fun dateTimeParse(input: String): Pair<String, String> {
        var date: Date? = null
        var formateDate = ""
        var time = ""
        try {
            val formatter = SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy")

            date = formatter.parse(input)
            formateDate = SimpleDateFormat("MM-dd-yyyy").format(date)
            time = SimpleDateFormat("HH:mm").format(date)
        } catch (e: ParseException) {
        }
        return Pair(formateDate, time)
    }
}