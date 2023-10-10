package com.example.einkaufsbegleiter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.einkaufsbegleiter.entities.LibraryItem
import com.example.einkaufsbegleiter.entities.NoteItem
import com.example.einkaufsbegleiter.entities.ShopListItem
import com.example.einkaufsbegleiter.entities.ShopListNameItem

// Diese abstrakte Klasse definiert die Haupt-Datenbank meiner App.
@Database (entities = [LibraryItem::class, NoteItem::class, ShopListItem::class, ShopListNameItem::class], version = 1)
abstract class MainDatabase: RoomDatabase() {
    abstract fun getDao(): Dao
    companion object{
        @Volatile
        private var INSTANCE: MainDatabase? = null

        // Diese Funktion erstellt oder gibt eine Instanz der Datenbank zurück.
        fun getDataBase(context: Context): MainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "shopping_list.db"
                ).build()
                instance
            }
        }
    }
}