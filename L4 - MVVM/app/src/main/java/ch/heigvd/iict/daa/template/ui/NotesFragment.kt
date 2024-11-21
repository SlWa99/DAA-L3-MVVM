package ch.heigvd.iict.daa.template.ui

import NotesAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModelFactory
import android.content.Context
import android.util.Log
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.template.data.AppDatabase
import ch.heigvd.iict.daa.template.repository.NoteRepository

class NotesFragment : Fragment() {

    private lateinit var notesAdapter: NotesAdapter
    private val noteViewModel: NoteViewModel by activityViewModels {
        val noteDao = AppDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(noteDao)
        NoteViewModelFactory(repository)
    }

    // todo tu dois mieux capter le lien entre le items de notesadapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialisation des composants de la vue
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_notes)
        recyclerView.layoutManager = LinearLayoutManager(context)

        notesAdapter = NotesAdapter()
        recyclerView.adapter = notesAdapter

        // Observer les données du ViewModel
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notesAndSchedules ->
            Log.d("NotesFragment", "Notes reçues: ${notesAndSchedules.size}")
            notesAdapter.updateItems(notesAndSchedules)
        }

        // todo capter et expliquer à val ... comment le viewmodel fait le tri avec cet énum ?
        noteViewModel._sortedNotes.observe(viewLifecycleOwner) { sortType ->
            when (sortType) {
                NoteViewModel.SortType.BY_DATE -> notesAdapter.sortByCreationDate()
                NoteViewModel.SortType.BY_SCHEDULE -> notesAdapter.sortBySchedule()
                NoteViewModel.SortType.NONE -> null
            }
        }
    }
}