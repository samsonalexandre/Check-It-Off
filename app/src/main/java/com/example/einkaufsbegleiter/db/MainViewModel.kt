package com.example.einkaufsbegleiter.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.einkaufsbegleiter.entities.NoteItem
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

// Diese Klasse ist ein ViewModel für die Notizen.
class MainViewModel(database: MainDatabase): ViewModel() {
    private val dao = database.getDao()

    val allNotes: LiveData<List<NoteItem>> = dao.getAllNotes().asLiveData()

    // Diese Funktion fügt eine Notiz zur Datenbank hinzu.
    fun insertNote(note: NoteItem) = viewModelScope.launch {
        dao.insertNote(note)
    }
    fun deleteNote(id: Int) = viewModelScope.launch {
        dao.deleteNote(id)
    }

    // Diese innere Klasse ist eine ViewModel Factory.
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