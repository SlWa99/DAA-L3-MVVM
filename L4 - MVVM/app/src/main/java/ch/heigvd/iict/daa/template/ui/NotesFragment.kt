package ch.heigvd.iict.daa.template.ui

import NotesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModelFactory
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import ch.heigvd.iict.daa.template.data.AppDatabase
import ch.heigvd.iict.daa.template.repository.NoteRepository
import ch.heigvd.iict.daa.template.ui.*
import androidx.lifecycle.Observer

class NotesFragment : Fragment() {

    private lateinit var notesAdapter: NotesAdapter
    private lateinit var noteViewModel: NoteViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val noteDao = AppDatabase.getDatabase(context).noteDao()
        val repository = NoteRepository(noteDao)
        val factory = NoteViewModelFactory(repository)
        noteViewModel = ViewModelProvider(this, factory)[NoteViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Charger le layout du fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        // Initialiser la RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_notes)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialisation de l'adaptateur
        notesAdapter = NotesAdapter()
        recyclerView.adapter = notesAdapter

        // Observer les notes et mettre à jour l'adaptateur
        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer { notesAndSchedules ->
            notesAndSchedules?.let {
                val notes = it.map { noteAndSchedule -> noteAndSchedule.note }
                notesAdapter.submitList(notes)
            }
        })

        return view
    }
}