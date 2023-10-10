package com.example.einkaufsbegleiter.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.databinding.ActivityMainBinding
import com.example.einkaufsbegleiter.fragments.FragmentManager
import com.example.einkaufsbegleiter.fragments.NoteFragment

// Diese Klasse repräsentiert die Hauptaktivität der App.
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // Diese Funktion wird aufgerufen, wenn die Aktivität erstellt wird.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                R.id.notes ->{
                    FragmentManager.setFragment(NoteFragment.newInstance(), this)
                    Log.d("MyLog", "Notes")
                }
                R.id.shop_list ->{
                    Log.d("MyLog", "List")
                }
                R.id.new_item ->{
                    FragmentManager.currentFrag?.onClickNew()
                    Log.d("MyLog", "New")
                }
            }
            true
        }
    }
}