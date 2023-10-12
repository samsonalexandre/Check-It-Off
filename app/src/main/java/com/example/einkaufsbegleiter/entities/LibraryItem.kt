package com.example.einkaufsbegleiter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Diese Klasse repräsentiert ein Eintrag in der Bibliothek.
@Entity (tableName = "library")
data class LibraryItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int?, // Die eindeutige ID des Bibliothekseintrags (wird automatisch generiert).

    @ColumnInfo (name = "name")
    val name: String // Der Name des Bibliothekseintrags.
)
