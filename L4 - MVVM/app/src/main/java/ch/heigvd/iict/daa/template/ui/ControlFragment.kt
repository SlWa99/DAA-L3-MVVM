package ch.heigvd.iict.daa.template.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.repository.NoteRepository
import ch.heigvd.iict.daa.template.entities.Note
import ch.heigvd.iict.daa.template.entities.*
import java.util.Calendar

class ControlFragment : Fragment() {

    private lateinit var noteRepository: NoteRepository
    private lateinit var noteCountTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_control, container, false)

        noteCountTextView = view.findViewById(R.id.note_count)

        // Obtenez les références des boutons
        val createButton: Button = view.findViewById(R.id.button_create_note)
        val deleteButton: Button = view.findViewById(R.id.button_delete_all)

        // Configurez les actions des boutons
        createButton.setOnClickListener {
            addRandomNote()
        }

        deleteButton.setOnClickListener {
            deleteAllNotes()
        }

        return view
    }

    private fun addRandomNote() {
        val newNote = Note(
            noteId = null,
            state = State.IN_PROGRESS,
            title = "Note aléatoire",
            text = "Contenu de la note aléatoire",
            creationDate = Calendar.getInstance(),
            type = Type.WORK
        )

        Thread {
            noteRepository.insert(newNote)
        }.start()
    }

    private fun deleteAllNotes() {
        Thread {
            noteRepository.deleteAllNotes()
        }.start()
    }
}