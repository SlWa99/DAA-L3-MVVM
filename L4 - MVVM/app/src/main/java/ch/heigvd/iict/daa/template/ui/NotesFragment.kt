/**
 * Nom du fichier : NotesFragment.kt
 * Description    : Fragment responsable de l'affichage des notes
 *                  et de leur gestion dans l'interface utilisateur.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.ui

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

/**
 * Fragment responsable de l'affichage des notes et de leur gestion dans l'interface utilisateur.
 */
class NotesFragment : Fragment() {

    private lateinit var notesAdapter: NotesAdapter

    // Initialisation du ViewModel partagé avec l'activité principale
    private val noteViewModel: NoteViewModel by activityViewModels {
        val noteDao = AppDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(noteDao)
        NoteViewModelFactory(repository)
    }

    /**
     * Crée et retourne la vue du fragment.
     *
     * @param inflater Pour charger la mise en page XML.
     * @param container Le conteneur parent.
     * @param savedInstanceState L'état sauvegardé, s'il existe.
     * @return La vue chargée pour ce fragment.
     */
    // todo tu dois mieux capter le lien entre le items de notesadapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notes, container, false)
    }

    /**
     * Méthode appelée après la création de la vue.
     * Configure le RecyclerView, l'adaptateur et observe les données du ViewModel.
     *
     * @param view La vue racine du fragment.
     * @param savedInstanceState L'état sauvegardé, s'il existe.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configuration du RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view_notes)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialisation de l'adaptateur
        notesAdapter = NotesAdapter()
        recyclerView.adapter = notesAdapter

        // Observer les notes et horaires dans le ViewModel
        noteViewModel.allNotes.observe(viewLifecycleOwner) { notesAndSchedules ->
            Log.d("NotesFragment", "Notes reçues: ${notesAndSchedules.size}")
            notesAdapter.updateItems(notesAndSchedules)
        }

        /**
         * Observer les changements de type de tri (_sortedNotes).
         * Applique le tri correspondant dans l'adaptateur en fonction de l'énumération [SortType].
         */
        // todo capter et expliquer à val ... comment le viewmodel fait le tri avec cet énum ?
        noteViewModel._sortedNotes.observe(viewLifecycleOwner) { sortType ->
            when (sortType) {
                // Tri par date de création des notes
                NoteViewModel.SortType.BY_DATE -> notesAdapter.sortByCreationDate()
                // Tri par date d'échéance (les notes sans échéance en dernier)
                NoteViewModel.SortType.BY_SCHEDULE -> notesAdapter.sortBySchedule()
                // Pas de tri spécifique
                NoteViewModel.SortType.NONE -> null
            }
        }
    }
}