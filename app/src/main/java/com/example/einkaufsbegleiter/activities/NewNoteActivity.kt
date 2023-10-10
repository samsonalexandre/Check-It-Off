package com.example.einkaufsbegleiter.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.ActivityNewNoteBinding
import com.example.einkaufsbegleiter.entities.NoteItem
import com.example.einkaufsbegleiter.fragments.NoteFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// Diese Klasse repräsentiert die Aktivität zum Erstellen einer neuen Notiz.
class NewNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewNoteBinding
    private var note: NoteItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Hier werden die Einstellungen für die Action Bar festgelegt.
        actionBarSettings()
        getNote()
    }

    private fun getNote() {
        val sNote = intent.getSerializableExtra(NoteFragment.NEW_NOTE_KEY)
        if (sNote != null) {
            note = sNote as NoteItem
            fillNote()
        }
    }

    private fun fillNote() = with(binding) {
        edTitle.setText(note?.title)
        edDescription.setText(note?.content)
    }
    // Diese Funktion erstellt das Optionsmenü in der Action Bar.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.new_note_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    // Diese Funktion behandelt die Auswahl von Optionen im Optionsmenü.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_save) {
            setMainResult()
        } else if (item.itemId == android.R.id.home) {
            finish()
        }

        return super.onOptionsItemSelected(item)
    }

    // Diese Funktion legt das Ergebnis für die Hauptaktivität fest, wenn eine Notiz gespeichert wird.
    private fun setMainResult() {
        var editState = "new"
        val tempNote: NoteItem?
        if (note == null) {
            tempNote = createNewNote()
        } else {
            editState = "update"
            tempNote = updateNote()
        }
        val i = Intent().apply {
            putExtra(NoteFragment.NEW_NOTE_KEY, tempNote)
            putExtra(NoteFragment.EDIT_STATE_KEY, editState)

//            putExtra(NoteFragment.TITLE_KEY, binding.edTitle.text.toString())
//            putExtra(NoteFragment.DESC_KEY, binding.edDescription.text.toString())
        }
        setResult(RESULT_OK, i)
        finish()
    }

    private fun updateNote(): NoteItem? = with (binding) {
        return note?.copy(
            title = edTitle.text.toString(),
            content = edDescription.text.toString()
            )
    }

    // Diese Funktion erstellt eine neue Notiz.
    private fun createNewNote(): NoteItem {
        return NoteItem(
            null,
            binding.edTitle.text.toString(),
            binding.edDescription.text.toString(),
            getCurrentTime(),
            ""
        )
    }

    // Diese Funktion gibt die aktuelle Zeit als formatierten String zurück.
    private fun  getCurrentTime(): String {
        val formatter = SimpleDateFormat("hh:mm:ss - dd.MM.yyyy", Locale.getDefault())
        return formatter.format(Calendar.getInstance().time)
    }

    // Diese Funktion konfiguriert die Action Bar.
    private fun actionBarSettings() {
        val ab = supportActionBar
        ab?.setDisplayHomeAsUpEnabled(true)
    }
}