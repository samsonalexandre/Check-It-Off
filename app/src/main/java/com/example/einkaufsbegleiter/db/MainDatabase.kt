package com.example.einkaufsbegleiter.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.einkaufsbegleiter.entities.LibraryItem
import com.example.einkaufsbegleiter.entities.NoteItem
import com.example.einkaufsbegleiter.entities.ShopListItem
import com.example.einkaufsbegleiter.entities.ShopListNameItem

// Diese Klasse repräsentiert die Hauptdatenbank der App und ist eine Room Database.
@Database (entities = [LibraryItem::class, NoteItem::class, ShopListItem::class, ShopListNameItem::class], version = 1)
abstract class MainDatabase: RoomDatabase() {
    // Diese abstrakte Funktion gibt ein Datenbankzugriffsschnittstellenobjekt (Dao) zurück.
    abstract fun getDao(): Dao
    // Begleitobjekt (Companion Object) für den Zugriff auf die Datenbankinstanz.
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