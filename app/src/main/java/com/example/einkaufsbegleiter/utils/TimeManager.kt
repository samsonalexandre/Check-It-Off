package com.example.einkaufsbegleiter.utils

import android.content.SharedPreferences
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.prefs.AbstractPreferences

// Diese Klasse verwaltet Zeitbezogene Operationen und bietet Funktionen zur Zeitformatierung.
object TimeManager {
    const val DEF_TIME_FORMAT = "hh:mm:ss - dd.MM.yyyy"
    // Diese Funktion gibt die aktuelle Zeit als formatierten String zurück.
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
    fun getTimeFormat(time: String, defPreferences: SharedPreferences): String {
        val defFormatter = SimpleDateFormat(DEF_TIME_FORMAT, Locale.getDefault())
        val defDate = defFormatter.parse(time)
        val newFormat = defPreferences.getString("time_format_key", DEF_TIME_FORMAT)
        val newFormatter = SimpleDateFormat(newFormat, Locale.getDefault())
        return if (defDate != null) {
            newFormatter.format(defDate)
        } else {
            time
        }
    }
}