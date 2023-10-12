package com.example.einkaufsbegleiter.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Diese Klasse verwaltet Zeitbezogene Operationen und bietet Funktionen zur Zeitformatierung.
object TimeManager {
    // Diese Funktion gibt die aktuelle Zeit als formatierten String zurück.
    fun getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - dd.MM.yyyy", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }
}