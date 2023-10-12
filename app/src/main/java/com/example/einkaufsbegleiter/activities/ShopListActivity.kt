package com.example.einkaufsbegleiter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.ActivityShopListBinding
import com.example.einkaufsbegleiter.db.MainViewModel
import com.example.einkaufsbegleiter.db.ShopListItemAdapter
import com.example.einkaufsbegleiter.dialogs.EditListItemDialog
import com.example.einkaufsbegleiter.entities.ShopListItem
import com.example.einkaufsbegleiter.entities.ShopListNameItem

//ShopListActivity dient dazu, die Einkaufsliste, für eine bestimmte Einkaufsliste anzuzeigen und zu bearbeiten
class ShopListActivity : AppCompatActivity(), ShopListItemAdapter.Listener {

    // Deklaration der benötigten Variablen und Instanzen
    private lateinit var binding: ActivityShopListBinding
    private var shopListNameItem: ShopListNameItem? = null
    private lateinit var saveItem: MenuItem
    private var edItem: EditText? = null
    private var adapter: ShopListItemAdapter? = null

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
        return true
    }

    // Die Methode überschreibt das Menüverhalten.
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.save_item) {
            // Wenn "Speichern" ausgewählt ist, wird ein neues Shop-List-Item hinzugefügt.
            addNewShopItem()
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
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
                saveItem.isVisible = false
                invalidateOptionsMenu()
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
}