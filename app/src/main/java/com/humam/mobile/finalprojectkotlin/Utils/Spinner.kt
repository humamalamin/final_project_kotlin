package com.humam.mobile.finalprojectkotlin.Utils

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

fun Spinner.loadFirstText(context: Context){
    val list = mutableListOf<String>()
    list.add("Please Wait...")

    adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, list)
}