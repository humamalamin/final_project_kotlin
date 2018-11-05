package com.humam.mobile.finalprojectkotlin.Utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTime {

    private fun formatDate(date: String, format: String, isDate: Boolean): String{
        val past = SimpleDateFormat(if (isDate) "yyyy-MM-dd" else "HH:mm:ss", Locale.US)

        var result = "";

        try{
            val pastData = past.parse(date)
            val newFormat = SimpleDateFormat(format, Locale.US)

            result = newFormat.format(pastData)
        }catch (e: ParseException){
            e.printStackTrace()
        }

        return result
    }

    fun getDateFormat(date: String?): String{
        return formatDate(date.toString(), "EEE, dd MM yyyy", true)
    }

    fun getTimeFormat(date: String?): String{
        return formatDate(date.toString(), "HH:mm",false)
    }
}