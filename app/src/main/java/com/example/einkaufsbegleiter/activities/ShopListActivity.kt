package com.example.einkaufsbegleiter.activities

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.ActivityShopListBinding
import com.example.einkaufsbegleiter.db.MainViewModel
import com.example.einkaufsbegleiter.db.ShopListItemAdapter
import com.example.einkaufsbegleiter.dialogs.EditListItemDialog
import com.example.einkaufsbegleiter.entities.LibraryItem
import com.example.einkaufsbegleiter.entities.ShopListItem
import com.example.einkaufsbegleiter.entities.ShopListNameItem
import com.example.einkaufsbegleiter.utils.ShareHelper

//ShopListActivity dient dazu, die Einkaufsliste, für eine bestimmte Einkaufsliste anzuzeigen und zu bearbeiten
class ShopListActivity : AppCompatActivity(), ShopListItemAdapter.Listener {

    // Deklaration der benötigten Variablen und Instanzen
    private lateinit var binding: ActivityShopListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem
    private var edItem: EditText? = null
    private var adapter: ShopListItemAdapter? = null
    private lateinit var textWatcher: TextWatcher

    // Instanziierung des MainViewModels mithilfe von activityViewModels
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Das Layout für die Aktivität wird initialisiert.
        binding = ActivityShopListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Die Methode wird aufgerufen, um die Aktivität zu initialisieren.
        init()
        // Die Methode wird aufgerufen, um die RecyclerView zu initialisieren.
        initRcView()
        // Die Methode wird aufgerufen, um Änderungen in den Shop-List-Items zu beobachten.
        listItemObserver()
    }

    // Die Methode überschreibt die Menüerstellung der Aktivität.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.shop_list_menu, menu)
        saveItem = menu?.findItem(R.id.save_item)!!
        val newItem = menu.findItem(R.id.new_item)!!
        edItem = newItem.actionView?.findViewById(R.id.edNewShopItem) as EditText
        newItem.setOnActionExpandListener(expandActionView())
        saveItem.isVisible = false
        textWatcher = textWatcher()
        return true
    }

    private fun textWatcher(): TextWatcher {
        return object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("MyLog", "On Text Changed: $s ")
                mainViewModel.getAllLibraryItems("%$s%")
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
    }

    // Die Methode überschreibt das Menüverhalten.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save_item -> {
                // Wenn "Speichern" ausgewählt ist, wird ein neues Shop-List-Item hinzugefügt.
                addNewShopItem()
            }
            R.id.delete_list -> {
                mainViewModel.deleteShopList(shopListNameItem?.id!!, true)
                finish()
            }
            R.id.clear_list -> {
                mainViewModel.deleteShopList(shopListNameItem?.id!!, false)
            }
            R.id.share_list -> {
                startActivity(Intent.createChooser(
                    ShareHelper.shareShopList(adapter?.currentList!!, shopListNameItem?.name!!),
                    "Teilen mit"
                    ))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Die Methode fügt ein neues Shop-List-Item hinzu.
    private fun addNewShopItem() {
        if (edItem?.text.toString().isEmpty())return
        val item = ShopListItem(
            null,
            edItem?.text.toString(),
            "",
            false,
            shopListNameItem?.id!!,
            0
        )
        edItem?.setText("")
        mainViewModel.insertShopItem(item)
    }

    // Die Methode beobachtet Änderungen in den Shop-List-Items.
    private fun listItemObserver() {
        mainViewModel.getAllItemsFromList(shopListNameItem?.id!!).observe(this) {
            adapter?.submitList(it)
            binding.tvEmpty.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    private fun libraryItemObserver() {
        mainViewModel.libraryItems.observe(this) {
            val tempShopList = ArrayList<ShopListItem>()
            it.forEach { item ->
                val shopItem = ShopListItem(
                    item.id,
                    item.name,
                    "",
                    false,
                    0,
                    1
                )
                tempShopList.add(shopItem)
            }
            adapter?.submitList(tempShopList)
            binding.tvEmpty.visibility = if (it.isEmpty()) {
                View.VISIBLE
            } else {
                View.GONE
            }
        }
    }

    // Die Methode initRcView() initialisiert die RecyclerView.
    private fun initRcView() = with(binding) {
        adapter = ShopListItemAdapter(this@ShopListActivity)
        rcView.layoutManager = LinearLayoutManager(this@ShopListActivity)
        rcView.adapter = adapter
    }

    // Die Methode erstellt und verwaltet das Verhalten der erweiterten Menüansicht.
    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem): Boolean {
                saveItem.isVisible = true
                edItem?.addTextChangedListener(textWatcher)
                libraryItemObserver()
                mainViewModel.getAllItemsFromList(shopListNameItem?.id!!).removeObservers(this@ShopListActivity)
                mainViewModel.getAllLibraryItems("%%")
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                saveItem.isVisible = false
                edItem?.removeTextChangedListener(textWatcher)
                invalidateOptionsMenu()
                mainViewModel.libraryItems.removeObservers(this@ShopListActivity)
                edItem?.setText("")
                libraryItemObserver()
                return true
            }

        }
    }

    // Die Methode init() initialisiert die Aktivität und extrahiert die übergebene Einkaufslisten-Entität.
    private fun init() {
        shopListNameItem = intent.getSerializableExtra(SHOP_LIST_NAME) as ShopListNameItem
    }

    companion object {
        // Konstante, die den Schlüssel für das Übergeben der Einkaufslisten-Entität zwischen Aktivitäten definiert.
        const val SHOP_LIST_NAME = "shop_list_name"
    }

    // Die Methode reagiert auf Klickereignisse in der RecyclerView.
    override fun onClickItem(shopListItem: ShopListItem, state: Int) {
        when(state) {
            ShopListItemAdapter.CHECK_BOX -> mainViewModel.updateListItem(shopListItem)
            ShopListItemAdapter.EDIT -> editListItem(shopListItem)
            ShopListItemAdapter.EDIT_LIBRARY_ITEM -> editLibraryItem(shopListItem)
            ShopListItemAdapter.DELETE_LIBRARY_ITEM -> {
                mainViewModel.deleteLibraryItem(shopListItem.id!!)
                mainViewModel.getAllLibraryItems("%${edItem?.text.toString()}%")
            }
        }

    }

    // Die Methode öffnet den Dialog zum Bearbeiten eines Shop-List-Items.
    private fun editListItem(item: ShopListItem) {
        EditListItemDialog.showDialog(this, item, object: EditListItemDialog.Listener {
            override fun onClick(item: ShopListItem) {
                mainViewModel.updateListItem(item)
            }

        })
    }
    private fun editLibraryItem(item: ShopListItem) {
        EditListItemDialog.showDialog(this, item, object: EditListItemDialog.Listener {
            override fun onClick(item: ShopListItem) {
                mainViewModel.updateLibraryItem(LibraryItem(item.id, item.name))
                mainViewModel.getAllLibraryItems("%${edItem?.text.toString()}%")
            }

        })
    }
}