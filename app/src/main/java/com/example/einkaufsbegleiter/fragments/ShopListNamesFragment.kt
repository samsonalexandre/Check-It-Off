package com.example.einkaufsbegleiter.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.einkaufsbegleiter.activities.MainApp
import com.example.einkaufsbegleiter.activities.ShopListActivity
import com.example.einkaufsbegleiter.databinding.FragmentShopListNamesBinding
import com.example.einkaufsbegleiter.db.MainViewModel
import com.example.einkaufsbegleiter.db.ShopListNameAdapter
import com.example.einkaufsbegleiter.dialogs.DeleteDialog
import com.example.einkaufsbegleiter.dialogs.NewListDialog
import com.example.einkaufsbegleiter.entities.ShopListNameItem
import com.example.einkaufsbegleiter.utils.TimeManager

// Diese Klasse repräsentiert ein Fragment zur Verwaltung von Einkaufslisten-Namen.
class ShopListNamesFragment : BaseFragment(), ShopListNameAdapter.Listener {
    private lateinit var binding: FragmentShopListNamesBinding
    private lateinit var adapter: ShopListNameAdapter

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    // Diese Funktion wird aufgerufen, wenn ein neuer Eintrag erstellt wird.
    override fun onClickNew() {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {
            override fun onClick(name: String) {
                Log.d("MyLog", "Name: $name")
                val shopListName = ShopListNameItem(
                    null,
                    name,
                    TimeManager.getCurrentTime(),
                    0,
                    0,
                    ""
                )
                mainViewModel.insertShopListName(shopListName)
            }

        }, "")
    }
    // Diese Funktion wird aufgerufen, wenn das Fragment erstellt wird.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    // Diese Funktion erstellt die Ansicht des Fragments.
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListNamesBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Diese Funktion wird nach dem Erstellen der Ansicht aufgerufen.
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    // Diese Funktion initialisiert die RecyclerView.
    private fun initRcView() = with(binding) {
        rcView.layoutManager = LinearLayoutManager(activity)
        adapter = ShopListNameAdapter(this@ShopListNamesFragment)
        rcView.adapter = adapter
    }

    // Diese Funktion beobachtet Änderungen an der Liste von Einkaufslisten-Namen.
    private fun observer() {
        mainViewModel.allShopListNamesItem.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    // Die 'newInstance' Funktion erstellt eine neue Instanz des Fragments.
    companion object {
        @JvmStatic
        fun newInstance() = ShopListNamesFragment()

    }

    // Diese Funktion wird aufgerufen, wenn ein Eintrag gelöscht werden soll.
    override fun deleteItem(id: Int) {
        DeleteDialog.showDialog(context as AppCompatActivity, object : DeleteDialog.Listener {
            override fun onClick() {
                mainViewModel.deleteShopListName(id)
            }

        })
    }

    // Diese Funktion wird aufgerufen, wenn ein Eintrag bearbeitet werden soll.
    override fun editItem(shopListNameItem: ShopListNameItem) {
        NewListDialog.showDialog(activity as AppCompatActivity, object : NewListDialog.Listener {
            override fun onClick(name: String) {
                Log.d("MyLog", "Name: $name")
                mainViewModel.updateListName(shopListNameItem.copy(name = name))
            }

        }, shopListNameItem.name)
    }

    // Diese Funktion wird aufgerufen, wenn auf einen Eintrag geklickt wird (bisher nicht implementiert).
    override fun onClickItem(shopListNameItem: ShopListNameItem) {
        val i = Intent(activity, ShopListActivity::class.java).apply {
            putExtra(ShopListActivity.SHOP_LIST_NAME, shopListNameItem)
        }
        startActivity(i)
    }
}