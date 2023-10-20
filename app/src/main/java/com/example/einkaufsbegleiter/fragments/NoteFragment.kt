package com.example.einkaufsbegleiter.fragments

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.einkaufsbegleiter.R
import com.example.einkaufsbegleiter.activities.MainApp
import com.example.einkaufsbegleiter.activities.NewNoteActivity
import com.example.einkaufsbegleiter.databinding.FragmentNoteBinding
import com.example.einkaufsbegleiter.db.MainViewModel
import com.example.einkaufsbegleiter.db.NoteAdapter
import com.example.einkaufsbegleiter.entities.NoteItem

// Diese Klasse repräsentiert das Fragment zur Anzeige von Notizen.
class NoteFragment : BaseFragment(), NoteAdapter.Listener {
    private lateinit var binding: FragmentNoteBinding
    private lateinit var editLauncher: ActivityResultLauncher<Intent>
    private lateinit var adapter: NoteAdapter
    private lateinit var defPref: SharedPreferences

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    // Diese Funktion wird aufgerufen, wenn auf "Neu" geklickt wird.
    override fun onClickNew() {
        editLauncher.launch(Intent(activity, NewNoteActivity::class.java))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onEditResult()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRcView()
        observer()
    }

    // Diese Funktion initialisiert die RecyclerView für die Anzeige von Notizen.
    private fun initRcView() = with(binding) {
        defPref = PreferenceManager.getDefaultSharedPreferences(requireActivity())//hier nicht so wie im video
        rcViewNote.layoutManager = getLayoutManager()
        adapter = NoteAdapter(this@NoteFragment, defPref)
        rcViewNote.adapter = adapter
    }

    //Wähle zwischen Linear und Grit Layout
    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return if (defPref.getString("note_style_key", "Linear") == "Linear") {
            LinearLayoutManager(activity)
        } else {
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    // Diese Funktion beobachtet Änderungen an der Liste von Notizen.
    private fun observer() {
        mainViewModel.allNotes.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    // Diese Funktion behandelt das Ergebnis, wenn eine neue Notiz erstellt wird.
    private fun onEditResult() {
        editLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                val editState = it.data?.getStringExtra(EDIT_STATE_KEY)
                if (editState == "update") {
                    mainViewModel.updateNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                } else {
                    mainViewModel.insertNote(it.data?.getSerializableExtra(NEW_NOTE_KEY) as NoteItem)
                    //Log.d("MyLog", "title: ${it.data?.getStringExtra(TITLE_KEY)}")
                    //Log.d("MyLog", "description: ${it.data?.getStringExtra(DESC_KEY)}")
                }
            }
        }
    }


    override fun deleteItem(id: Int) {
        mainViewModel.deleteNote(id)
    }

    override fun onClickItem(note: NoteItem) {
        val intent = Intent(activity, NewNoteActivity::class.java).apply {
            putExtra(NEW_NOTE_KEY, note)
        }
        editLauncher.launch(intent)
    }

    companion object {
        const val NEW_NOTE_KEY = "new_note_key"
        const val EDIT_STATE_KEY = "edit_state_key"

        // const val TITLE_KEY = "title_key"
        // const val DESC_KEY = "desc_key"
        @JvmStatic
        fun newInstance() = NoteFragment()

    }
}