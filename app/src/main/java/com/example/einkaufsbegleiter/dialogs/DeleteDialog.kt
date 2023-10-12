package com.example.einkaufsbegleiter.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.einkaufsbegleiter.databinding.DeleteDialogBinding
import com.example.einkaufsbegleiter.databinding.NewListDialogBinding

// Diese Klasse stellt einen Löschdialog dar.
object DeleteDialog {
    // Diese Methode zeigt den Löschdialog an.
    fun showDialog(context: Context, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = DeleteDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            // Wenn der Lösch-Button geklickt wird, wird der entsprechende Listener aufgerufen, und der Dialog wird geschlossen.
            bDelete.setOnClickListener {
                listener.onClick()
                dialog?.dismiss()
            }
            // Wenn der Abbrechen-Button geklickt wird, wird der Dialog geschlossen, ohne eine Aktion auszuführen.
            bCancel.setOnClickListener {
                dialog?.dismiss()
            }
        }
        dialog = builder.create()
        // Das Hintergrundbild des Dialogs wird auf null gesetzt, um ein Standardhintergrund zu vermeiden.
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    // Diese Schnittstelle ermöglicht die Kommunikation von Löschereignissen an die übergeordnete Ansicht.
    interface Listener {
        fun onClick()
    }
}