package com.example.einkaufsbegleiter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

// Diese Klasse repräsentiert ein Einkaufslisten-Objekt.
@Entity (tableName = "shopping_list_names") //Diese Annotation gibt den Namen der Tabelle in der Datenbank an, in der diese Entität gespeichert wird.
data class ShopListNameItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int?, //Dieses Feld repräsentiert die eindeutige ID jeder Einkaufsliste. Die ID wird automatisch generiert.

    @ColumnInfo (name = "name")
    val name: String, //Hier wird der Name der Einkaufsliste gespeichert.

    @ColumnInfo (name = "time")
    val time: String, //Dieses Feld speichert den Zeitstempel, wann die Einkaufsliste erstellt oder zuletzt aktualisiert wurde.

    @ColumnInfo (name = "allItemCount")
    val appItemCounter: Int, //Dieses Feld enthält die Gesamtanzahl der Artikel in der Einkaufsliste.

    @ColumnInfo (name = "checkedItemCounter")
    val checkedItemCounter: Int, //Hier wird die Anzahl der bereits abgehakten Artikel in der Einkaufsliste gespeichert.

    @ColumnInfo (name = "itemsIds")
    val itemsIds: String, //

): Serializable //Die Klasse implementiert das Serializable-Interface, was bedeutet, dass Objekte dieser Klasse in serialisierbarer
                // Form übertragen werden können. Dies ist nützlich, wenn Objekte zwischen Aktivitäten oder Fragmenten übergeben werden.