package com.example.einkaufsbegleiter.db

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.ListNameItemBinding
import com.example.einkaufsbegleiter.databinding.ShopLibraryListItemBinding
import com.example.einkaufsbegleiter.databinding.ShopListItemBinding
import com.example.einkaufsbegleiter.entities.ShopListItem

// Diese Klasse ist ein RecyclerView-Adapter für Shop-List-Items.
class ShopListItemAdapter(private val listener: Listener) :
    ListAdapter<ShopListItem, ShopListItemAdapter.ItemHolder>(ItemComporator()) {

    // Diese Methode wird aufgerufen, um das View-Objekt für ein RecyclerView-Element zu erstellen.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return if (viewType == 0) ItemHolder.createShopItem(parent)
        else
            ItemHolder.createLibraryItem(parent)
    }

    // Diese Methode wird aufgerufen, um Daten an ein RecyclerView-Element zu binden.
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        if (getItem(position).itemType == 0) {
            holder.setItemData(getItem(position), listener)
        } else {
            holder.setLibraryData(getItem(position), listener)
        }
    }

    // Diese Methode gibt den View-Typ für ein Element in der Liste zurück.
    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType
    }

    // Diese innere Klasse repräsentiert ein einzelnes RecyclerView-Element.
    class ItemHolder(val view: View) : RecyclerView.ViewHolder(view) {

        // Diese Methode bindet Shop-List-Item-Daten an die Ansicht.
        fun setItemData(shopListItem: ShopListItem, listener: Listener) {
            val binding = ShopListItemBinding.bind(view)
            binding.apply {
                tvName.text = shopListItem.name
                tvInfo.text = shopListItem.itemInfo
                tvInfo.visibility = infoVisibility(shopListItem)
                chBox.isChecked = shopListItem.itemChcked
                setPaintFlagAndColor(binding)
                chBox.setOnClickListener {
                    listener.onClickItem(shopListItem.copy(itemChcked = chBox.isChecked), CHECK_BOX)
                }
                imEdit.setOnClickListener {
                    listener.onClickItem(shopListItem, EDIT)
                }
            }
        }

        // Diese Methode bindet Shop-Library-Item-Daten an die Ansicht.
        fun setLibraryData(shopListItem: ShopListItem, listener: Listener) {
            val binding = ShopLibraryListItemBinding.bind(view)
            binding.apply {
                tvName.text = shopListItem.name
                imEdit.setOnClickListener {
                    listener.onClickItem(shopListItem, EDIT_LIBRARY_ITEM)
                }
                imDelete.setOnClickListener {
                    listener.onClickItem(shopListItem, DELETE_LIBRARY_ITEM)
                }
                itemView.setOnClickListener {
                    listener.onClickItem(shopListItem, ADD)
                }
            }

        }

        // Diese Methode setzt Textfarbeneigenschaften für gestrichelte oder normale Anzeige.
        private fun setPaintFlagAndColor(binding: ShopListItemBinding) {
            binding.apply {
                if (chBox.isChecked) {
                    tvName.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvInfo.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    tvName.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.grey_light
                        )
                    )
                    tvInfo.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.grey_light
                        )
                    )

                } else {
                    tvName.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvInfo.paintFlags = Paint.ANTI_ALIAS_FLAG
                    tvName.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                    tvInfo.setTextColor(ContextCompat.getColor(binding.root.context, R.color.black))
                }
            }

        }

        // Diese Methode bestimmt die Sichtbarkeit des Info-Textfelds.
        private fun infoVisibility(shopListItem: ShopListItem): Int {
            return if (shopListItem.itemInfo.isEmpty()) {
                View.GONE
            } else {
                View.VISIBLE
            }
        }

        // Diese innere Klasse repräsentiert ein RecyclerView-Element für Shop-List-Items.
        companion object {
            fun createShopItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_list_item, parent, false)
                )
            }

            // Diese Methode erstellt ein RecyclerView-Element für Shop-Library-Items, falls erforderlich.
            fun createLibraryItem(parent: ViewGroup): ItemHolder {
                return ItemHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.shop_library_list_item, parent, false)
                )
            }
        }
    }

    // Diese innere Klasse vergleicht Shop-List-Items für die Aktualisierung der RecyclerView.
    class ItemComporator : DiffUtil.ItemCallback<ShopListItem>() {
        override fun areItemsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShopListItem, newItem: ShopListItem): Boolean {
            return oldItem == newItem
        }

    }

    // Ein Listener-Interface für Interaktionen mit den RecyclerView-Elementen.
    interface Listener {
        fun onClickItem(shopListItem: ShopListItem, state: Int)
    }

    // Konstanten für die verschiedenen Aktionen.
    companion object {
        const val EDIT = 0
        const val CHECK_BOX = 1
        const val EDIT_LIBRARY_ITEM = 2
        const val DELETE_LIBRARY_ITEM = 3
        const val ADD = 4
    }
}