package ch.heigvd.iict.daa.template.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.repository.NoteRepository
import kotlinx.coroutines.launch

//TODO --> je crois qu'on peut mettre une seule classe pour les deux fragments (manipulation de la liste)
class ControlFragment : Fragment() {

    private lateinit var noteRepository: NoteRepository
    private lateinit var noteCountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_control, container, false)

        noteCountTextView = view.findViewById(R.id.note_count)

        val createButton: Button = view.findViewById(R.id.button_create_note)
        val deleteButton: Button = view.findViewById(R.id.button_delete_all)

        createButton.setOnClickListener {
            addRandomNote()
        }

        deleteButton.setOnClickListener {
            deleteAllNotes()
        }

        return view
    }

    private fun addRandomNote() {
        viewLifecycleOwner.lifecycleScope.launch {
            noteRepository.generateANote()
        }
    }

    private fun deleteAllNotes() {
        viewLifecycleOwner.lifecycleScope.launch {
            noteRepository.deleteAllNotes()
        }
    }
}