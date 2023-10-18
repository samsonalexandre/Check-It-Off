package com.example.einkaufsbegleiter.db

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.ListNameItemBinding
import com.example.einkaufsbegleiter.databinding.NoteListItemBinding
import com.example.einkaufsbegleiter.entities.NoteItem
import com.example.einkaufsbegleiter.entities.ShopListNameItem
import com.example.einkaufsbegleiter.utils.HtmlManager

// Diese Klasse ist ein RecyclerView-Adapter für Einkaufslisten.
class ShopListNameAdapter(private val listener: Listener): ListAdapter<ShopListNameItem, ShopListNameAdapter.ItemHolder>(ItemComporator()) {

    // Diese Methode erstellt einen neuen Eintragsholder, wenn benötigt.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder.create(parent)
    }

    // Diese Methode bindet Daten an einen Eintragsholder und füllt die Ansicht mit den Einkaufslistendaten.
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.setData(getItem(position), listener)
    }

    // Diese innere Klasse repräsentiert einen Eintrag in der RecyclerView.
    class ItemHolder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ListNameItemBinding.bind(view)

        fun setData(shopListNameItem: ShopListNameItem, listener: Listener) = with(binding) {
            tvListName.text = shopListNameItem.name
            tvTime.text = shopListNameItem.time
            pBar.max = shopListNameItem.allItemCounter
            pBar.progress = shopListNameItem.checkedItemCounter
            val colorState = ColorStateList.valueOf(getProgressColorState(shopListNameItem, binding.root.context))
            pBar.progressTintList = colorState
            counterCard.backgroundTintList = colorState
            val counterText = "${shopListNameItem.checkedItemCounter}/${shopListNameItem.allItemCounter}"
            tvCounter.text = counterText
            itemView.setOnClickListener {
                listener.onClickItem(shopListNameItem)
            }

            imDelete.setOnClickListener {
                // Löschen-Aktion wird an den Listener übergeben.
                listener.deleteItem(shopListNameItem.id!!)
            }

            imEdit.setOnClickListener {
                // Bearbeiten-Aktion wird an den Listener übergeben.
                listener.editItem(shopListNameItem)
            }

        }

        private fun getProgressColorState(item: ShopListNameItem, context: Context): Int {
            return if (item.checkedItemCounter == item.allItemCounter) {
                ContextCompat.getColor(context, R.color.green_main)
            } else {
                ContextCompat.getColor(context, R.color.red_main)
            }
        }

        // Diese Methode erstellt einen neuen Eintragsholder.
        companion object {
            fun create(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context).
                    inflate(R.layout.list_name_item, parent, false))
            }
        }
    }

    // Diese innere Klasse vergleicht Einträge für die RecyclerView, um Änderungen effizient zu erkennen.
    class ItemComporator: DiffUtil.ItemCallback<ShopListNameItem>() {
        override fun areItemsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListNameItem, newItem: ShopListNameItem): Boolean {
            return oldItem == newItem
        }

    }

    // Diese Schnittstelle ermöglicht die Kommunikation von Klickereignissen an die übergeordnete Ansicht.
    interface Listener {
        fun deleteItem(id: Int)
        fun editItem(shopListName: ShopListNameItem)
        fun onClickItem(shopListName: ShopListNameItem)
    }
}