package com.example.einkaufsbegleiter.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

object TimeManager {
    // Diese Funktion gibt die aktuelle Zeit als formatierten String zurück.
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - dd.MM.yyyy", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}