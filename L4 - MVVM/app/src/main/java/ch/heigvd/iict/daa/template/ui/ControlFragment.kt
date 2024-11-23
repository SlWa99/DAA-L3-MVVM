/**
 * Nom du fichier : ControlFragment.kt
 * Description    : Fragment contrôleur pour gérer l'ajout et la suppression de notes
 *                  dans l'interface utilisateur.
 *                  Permet d'ajouter une note aléatoire et de supprimer toutes les notes existantes.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.data.AppDatabase
import ch.heigvd.iict.daa.template.databinding.FragmentControlBinding
import ch.heigvd.iict.daa.template.repository.NoteRepository
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModelFactory

/**
 * Fragment contrôleur pour gérer l'ajout et la suppression de notes dans l'interface utilisateur.
 * Permet d'ajouter une note aléatoire et de supprimer toutes les notes existantes.
 */
class ControlFragment : Fragment() {

    private lateinit var noteRepository: NoteRepository
    private lateinit var noteCountTextView: TextView

    private lateinit var binding: FragmentControlBinding

    // Initialisation du ViewModel partagé avec l'activité principale
    private val viewModel: NoteViewModel by activityViewModels {
        val noteDao = AppDatabase.getDatabase(requireContext()).noteDao()
        val repository = NoteRepository(noteDao)
        NoteViewModelFactory(repository)
    }

    /**
     * Initialise et retourne la vue associée au fragment.
     *
     * @param inflater Permet de charger le layout XML.
     * @param container Le conteneur dans lequel la vue est placée.
     * @param savedInstanceState État sauvegardé du fragment.
     * @return La vue créée.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val view = inflater.inflate(R.layout.fragment_control, container, false)
//        noteCountTextView = view.findViewById(R.id.note_count)
//
//        val createButton: Button = view.findViewById(R.id.button_create_note)
//        val deleteButton: Button = view.findViewById(R.id.button_delete_all)
//
//        createButton.setOnClickListener {
//            addRandomNote()
//        }
//
//        deleteButton.setOnClickListener {
//            deleteAllNotes()
//        }
//
//        return view

        binding = FragmentControlBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countNotes.observe(viewLifecycleOwner) {
            binding.noteCount.text = getString(R.string.note_database).format(it)
        }

        binding.buttonCreateNote.setOnClickListener {viewModel.generateANote()}
        binding.buttonDeleteAll.setOnClickListener {viewModel.deleteAllNotes()}

    }

//    /**
//     * Ajoute une note aléatoire en utilisant le repository.
//     */
//    private fun addRandomNote() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            noteRepository.generateANote()
//        }
//    }
//
//    /**
//     * Supprime toutes les notes en utilisant le repository.
//     */
//    private fun deleteAllNotes() {
//        viewLifecycleOwner.lifecycleScope.launch {
//            noteRepository.deleteAllNotes()
//        }
//    }
}