package com.example.einkaufsbegleiter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.ActivityMainBinding
import com.example.einkaufsbegleiter.dialogs.NewListDialog
import com.example.einkaufsbegleiter.fragments.FragmentManager
import com.example.einkaufsbegleiter.fragments.NoteFragment
import com.example.einkaufsbegleiter.fragments.ShopListNamesFragment

// Diese Klasse repräsentiert die Hauptaktivität der App.
class MainActivity : AppCompatActivity(), NewListDialog.Listener {
    private lateinit var binding: ActivityMainBinding

    // Diese Funktion wird aufgerufen, wenn die Aktivität erstellt wird.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)

        // Hier wird ein Listener für die Bottom Navigation View gesetzt.
        setBottomNavListener()
    }

    // Diese Funktion setzt den Listener für die Bottom Navigation View.
    private fun setBottomNavListener() {
        binding.bNav.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.settings ->{
                    Log.d("MyLog", "Settings")
                }
                // Wechselt zur Notiz-Ansicht (NoteFragment) und aktualisiert die Ansicht.
                R.id.notes ->{
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                    Log.d("MyLog", "Notes")
                }
                // Wechselt zur Einkaufslisten-Ansicht (ShopListNamesFragment) und aktualisiert die Ansicht.
                R.id.shop_list ->{
                    FragmentManager.setFragment(ShopListNamesFragment.newInstance(), this)
                    Log.d("MyLog", "List")
                }
                // Ruft die Funktion onClickNew im aktuellen Fragment auf.
                R.id.new_item ->{
                    FragmentManager.currentFrag?.onClickNew()
                    Log.d("MyLog", "New")
                }
            }
            true
        }
    }

    // Diese Funktion wird aufgerufen, wenn der "Neu" Button in NewListDialog geklickt wird.
    override fun onClick(name: String) {
        Log.d("MyLog", "$name")
    }
}