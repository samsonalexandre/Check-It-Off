package com.example.einkaufsbegleiter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Diese Klasse repräsentiert ein Eintrag in einer Einkaufsliste.
@Entity (tableName = "shop_list_item")
data class ShopListItem(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,

    @ColumnInfo (name = "name")
    val name: String,

    @ColumnInfo (name = "itemInfo")
    val itemInfo: String = "",

    @ColumnInfo (name = "itemChecked")
    val itemChcked: Boolean = false,

    @ColumnInfo (name = "listId")
    val listId: Int,

    @ColumnInfo (name = "itemType")
    val itemType: Int = 0
)
