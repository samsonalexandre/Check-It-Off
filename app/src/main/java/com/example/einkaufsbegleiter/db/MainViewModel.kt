package com.example.einkaufsbegleiter.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.einkaufsbegleiter.entities.NoteItem
import com.example.einkaufsbegleiter.entities.ShopListNameItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// Diese Klasse ist ein ViewModel für die Notizen und Einkaufslisten.
class MainViewModel(database: MainDatabase): ViewModel() {
    private val dao = database.getDao()

    // LiveData-Objekt, das eine Liste aller Notizen aus der Datenbank darstellt.
    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()
    // LiveData-Objekt, das eine Liste aller Einkaufslisten aus der Datenbank darstellt.
    val allShopListNamesItem: LiveData<List<ShopListNameItem>> = dao.getAllShopListNames().asLiveData()

    // Diese Funktion fügt eine Notiz zur Datenbank hinzu.
    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }

    // Diese Funktion fügt eine Einkaufsliste zur Datenbank hinzu.
    fun insertShopListName(listName: ShopListNameItem) = viewModelScope.launch {
        dao.insertShopListName(listName)
    }

    // Diese Funktion aktualisiert eine vorhandene Notiz in der Datenbank.
    fun updateNote(note: NoteItem) = viewModelScope.launch {
        dao.updateNote(note)
    }

    // Diese Funktion aktualisiert den Namen einer Einkaufsliste in der Datenbank.
    fun updateListName(shopListName: ShopListNameItem) = viewModelScope.launch {
        dao.updateListName(shopListName)
    }

    // Diese Funktion löscht eine Notiz aus der Datenbank anhand ihrer ID.
    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    // Diese Funktion löscht eine Einkaufsliste aus der Datenbank anhand ihrer ID.
    fun deleteShopListName(id: Int) = viewModelScope.launch {
        dao.deleteShopListName(id)
    }

    // Diese innere Klasse ist eine ViewModel Factory, die verwendet wird, um eine Instanz des ViewModels zu erstellen.
    class MainViewModelFactory(private val database: MainDatabase): ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(database) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}