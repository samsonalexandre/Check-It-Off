package com.example.einkaufsbegleiter.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.einkaufsbegleiter.entities.NoteItem
import com.example.einkaufsbegleiter.entities.ShopListNameItem
import kotlinx.coroutines.flow.Flow

// Dieses Interface definiert die Datenbankzugriffsmethoden für Notizen.
@Dao
interface Dao {
    @Query ("SELECT * FROM note_list")
    fun getAllNotes(): Flow<List<NoteItem>>
    @Query ("SELECT * FROM shopping_list_names")
    fun getAllShopListNames(): Flow<List<ShopListNameItem>>
    @Query ("DELETE FROM note_list WHERE id IS :id")
    suspend fun deleteNote(id: Int)
    @Insert
    suspend fun insertNote(note: NoteItem)
    @Insert
    suspend fun insertShopListName(name: ShopListNameItem)
    @Update
    suspend fun updateNote(note: NoteItem)
}