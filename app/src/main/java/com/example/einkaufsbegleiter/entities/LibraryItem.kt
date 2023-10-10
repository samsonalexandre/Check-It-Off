package com.example.einkaufsbegleiter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Diese Klasse repräsentiert ein Eintrag in der Bibliothek.
@Entity (tableName = "library")
data class LibraryItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,

    @ColumnInfo (name = "name")
    val name: String
)
