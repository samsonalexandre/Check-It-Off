package com.example.einkaufsbegleiter.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.NewListDialogBinding

// Diese Klasse stellt einen Dialog zum Erstellen oder Aktualisieren einer Einkaufsliste dar.
object NewListDialog {
    // Diese Methode zeigt den Dialog an und ermöglicht es dem Benutzer, eine Liste zu erstellen oder zu aktualisieren.
    fun showDialog(context: Context, listener: Listener, name: String) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            // Den Text des Eingabefelds mit dem übergebenen Namen füllen.
            edNewListName.setText(name)
            // Wenn ein Name vorhanden ist, den Text des Erstellen-Buttons aktualisieren.
            if (name.isNotEmpty()) bCreate.text = context.getString(R.string.update)
            // Wenn der Erstellen-Button geklickt wird, den eingegebenen Namen an den Listener übergeben und den Dialog schließen.
            bCreate.setOnClickListener {
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()) {
                    listener.onClick(listName)
                }
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        // Das Hintergrundbild des Dialogs wird auf null gesetzt, um ein Standardhintergrund zu vermeiden.
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    // Diese Schnittstelle ermöglicht die Kommunikation von Erstellungs- oder Aktualisierungsereignissen an die übergeordnete Ansicht.
    interface Listener {
        fun onClick(name: String)
    }
}