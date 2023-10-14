package com.example.einkaufsbegleiter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.einkaufsbegleiter.entities.NoteItem
import com.example.einkaufsbegleiter.entities.ShopListItem
import com.example.einkaufsbegleiter.entities.ShopListNameItem
import kotlinx.coroutines.flow.Flow

// Dieses Interface definiert die Datenbankzugriffsmethoden für Notizen.
@Dao
interface Dao {
    @Query ("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>

    @Query ("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShopListNameItem>>

    @Query ("SELECT * FROM shop_list_item WHERE listId LIKE :listId")
    fun getAllShopListItems(listId: Int): Flow<List<ShopListItem>>

    @Query ("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)

    @Query ("DELETE FROM shopping_list_names WHERE id IS :id")
    suspend fun deleteShopListName(id: Int)

    @Query ("DELETE FROM shop_list_item WHERE listId LIKE :listId")
    fun deleteShopItemsByListId(listId: Int)

    @Insert
    suspend fun insertNote(note: NoteItem)
    @Insert
    suspend fun insertItem(shopListItem: ShopListItem)
    @Insert
    suspend fun insertShopListName(name: ShopListNameItem)
    @Update
    suspend fun updateNote(note: NoteItem)
    @Update
    suspend fun updateListItem(item: ShopListItem)
    @Update
    suspend fun updateListName(shopListName: ShopListNameItem)
}