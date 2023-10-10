package com.example.einkaufsbegleiter.activities

import android.app.Application
import com.example.einkaufsbegleiter.db.MainDatabase

// Diese Klasse repräsentiert die Anwendungsklasse meiner App.
class MainApp: Application() {
    val database by lazy { MainDatabase.getDataBase(this) }
}