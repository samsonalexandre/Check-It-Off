package com.example.einkaufsbegleiter.dialogs

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.EditListItemDialogBinding
import com.example.einkaufsbegleiter.databinding.NewListDialogBinding
import com.example.einkaufsbegleiter.entities.ShopListItem

// Diese Klasse repräsentiert einen Dialog zum Bearbeiten eines Shop-List-Items.
object EditListItemDialog {
    // Zeigt den Dialog an und ermöglicht dem Benutzer, ein Shop-List-Item zu bearbeiten.
    fun showDialog(context: Context,item: ShopListItem, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = EditListItemDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            // Setzt die ursprünglichen Werte des Shop-List-Items in die Textfelder des Dialogs.
            edName.setText(item.name)
            edInfo.setText(item.itemInfo)
            // Bei Klick auf die "Update"-Schaltfläche wird das bearbeitete Item zurückgegeben.
            bUpdate.setOnClickListener {
                if (edName.text.toString().isNotEmpty()) {
                    listener.onClick(item.copy(name = edName.text.toString(), itemInfo = edInfo.text.toString()))
                }
                dialog?.dismiss()
            }

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)
        dialog.show()
    }

    // Ein Listener-Interface, um die Änderungen am Shop-List-Item zu übertragen.
    interface Listener {
        fun onClick(item: ShopListItem)
    }
}