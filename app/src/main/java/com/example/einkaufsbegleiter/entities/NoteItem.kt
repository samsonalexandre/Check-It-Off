package com.example.einkaufsbegleiter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Diese Klasse repräsentiert ein Notiz-Objekt.
@Entity (tableName = "note_list")
data class NoteItem (
    @PrimaryKey (autoGenerate = true)
    val id: Int?, // Die eindeutige ID der Notiz (wird automatisch generiert).
    @ColumnInfo (name = "title")
    val title: String, // Der Titel der Notiz.
   @ColumnInfo (name = "content")
    val content: String, // Der Inhalt der Notiz.
    @ColumnInfo (name = "time")
    val time: String, // Die Zeit, zu der die Notiz erstellt wurde.
    @ColumnInfo (name = "category")
    val category: String // Die Kategorie, zu der die Notiz gehört (optional).
): Serializable