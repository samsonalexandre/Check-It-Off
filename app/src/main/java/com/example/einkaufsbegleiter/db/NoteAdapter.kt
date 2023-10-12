package com.example.einkaufsbegleiter.db

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.NoteListItemBinding
import com.example.einkaufsbegleiter.entities.NoteItem
import com.example.einkaufsbegleiter.utils.HtmlManager

// Diese Klasse ist ein RecyclerView-Adapter für die Anzeige von Notizen in einer RecyclerView.

class NoteAdapter(private val listener: Listener): ListAdapter<NoteItem, NoteAdapter.ItemHolder>(ItemComporator()) {

    // Erstellt und gibt einen neuen Eintragsholder zurück, wenn benötigt.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    // Bindet Daten an einen Eintragsholder und füllt die Ansicht mit den Daten aus dem entsprechenden Notizeneintrag.
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    // Diese innere Klasse repräsentiert einen Eintrag in der RecyclerView.
    class ItemHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = NoteListItemBinding.bind(view)

        // Setzt Daten in die Ansichtselemente des Eintragsholders.
        fun setData(note: NoteItem, listener: Listener) = with(binding) {
            tvTitle.text = note.title
            tvDescription.text = HtmlManager.getFromHtml(note.content).trim()
            tvTime.text = note.time

            // Ein Klick-Listener für den gesamten Eintrag, um auf den Eintrag zu klicken.
            itemView.setOnClickListener {
                listener.onClickItem(note)
            }
            // Ein Klick-Listener für das Löschen-Symbol, um den Eintrag zu löschen.
            imDelete.setOnClickListener {
                listener.deleteItem(note.id!!)
            }
        }
        companion object {
            // Erstellt einen neuen Eintragsholder, indem das Layout aufgeblasen wird.
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                    inflate(R.layout.note_list_item, parent, false))
            }
        }
    }

    // Diese innere Klasse vergleicht Einträge für die RecyclerView, um Änderungen effizient zu erkennen.
    class ItemComporator: DiffUtil.ItemCallback<NoteItem>() {
        override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
            return oldItem == newItem
        }

    }

    // Eine Schnittstelle zur Kommunikation von Klickereignissen an die übergeordnete Ansicht.
    interface Listener {
        fun deleteItem(id: Int)
        fun onClickItem(note: NoteItem)
    }
}