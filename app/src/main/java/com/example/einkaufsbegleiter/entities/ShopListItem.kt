package com.example.einkaufsbegleiter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Diese Klasse repräsentiert ein Eintrag in einer Einkaufsliste.
@Entity (tableName = "shop_list_item") //Diese Annotation gibt den Namen der Tabelle in der Datenbank an, in der diese Entität gespeichert wird.
data class ShopListItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int?, //Dieses Feld repräsentiert die eindeutige Kennung (ID) eines Einkaufsliste-Eintrags und wird automatisch generiert.

    @ColumnInfo (name = "name")
    val name: String, //Dieses Feld speichert den Namen oder die Bezeichnung des Einkaufsliste-Eintrags.

    @ColumnInfo (name = "itemInfo")
    val itemInfo: String = "", //Dieses Feld speichert zusätzliche Informationen oder Beschreibungen des Eintrags. Es ist optional und kann leer sein.

    @ColumnInfo (name = "itemChecked")
    val itemChcked: Boolean = false, //Dieses Feld gibt an, ob der Eintrag als erledigt markiert ist. Standardmäßig ist er auf "false" gesetzt.

    @ColumnInfo (name = "listId")
    val listId: Int, //Dieses Feld enthält die ID der Einkaufsliste, zu der dieser Eintrag gehört.

    @ColumnInfo (name = "itemType")
    val itemType: Int = 0 //Dieses Feld kann den Typ oder die Kategorie des Eintrags speichern, falls erforderlich. Standardmäßig ist es auf den Wert 0 gesetzt.
)
