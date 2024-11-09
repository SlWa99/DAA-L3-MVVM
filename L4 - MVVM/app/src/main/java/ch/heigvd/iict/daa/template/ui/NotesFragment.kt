package ch.heigvd.iict.daa.template.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.entities.Note

class NotesFragment : Fragment() {

    private lateinit var notesAdapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Charger le layout du fragment
        val view = inflater.inflate(R.layout.fragment_notes, container, false)

        // Initialiser la RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_notes)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Générer une liste de notes aléatoires pour l'exemple
        val notesList: List<Note> = List(10) { Note.generateRandomNote() }

        // Configurer l'adaptateur
        notesAdapter = NotesAdapter(notesList)
        recyclerView.adapter = notesAdapter

        return view
    }
}