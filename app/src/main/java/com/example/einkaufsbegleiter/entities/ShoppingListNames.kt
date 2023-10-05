package com.example.einkaufsbegleiter.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

//Einkaufslisten model
@Entity (tableName = "shopping_list_names")
data class ShoppingListNames(
    @PrimaryKey (autoGenerate = true)
    val id: Int?,

    @ColumnInfo (name = "name")
    val name: String,

    @ColumnInfo (name = "time")
    val time: String,

    @ColumnInfo (name = "allItemCount")
    val appItemCounter: Int,

    @ColumnInfo (name = "checkedItemCounter")
    val checkedItemCounter: Int,

    @ColumnInfo (name = "itemsIds")
    val itemsIds: String,

): Serializable