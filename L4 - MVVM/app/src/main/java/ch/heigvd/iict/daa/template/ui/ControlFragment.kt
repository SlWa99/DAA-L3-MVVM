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
        binding = FragmentControlBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.countNotes.observe(viewLifecycleOwner) { count ->
            if (count?.toInt() == 0) {
                binding.noteCount.text = getString(R.string.notes_counter_empty) // Affiche "No notes"
            } else {
                binding.noteCount.text = getString(R.string.notes_counter).format(count) // Affiche le nombre
            }
        }
        binding.buttonCreateNote.setOnClickListener {viewModel.generateANote()}
        binding.buttonDeleteAll.setOnClickListener {viewModel.deleteAllNotes()}
    }
}