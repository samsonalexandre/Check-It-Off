package com.example.einkaufsbegleiter.activities

import android.app.Application
import com.example.einkaufsbegleiter.db.MainDatabase

class MainApp: Application() {
    val database by lazy { MainDatabase.getDataBase(this) }
}