package ch.heigvd.iict.daa.template.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ch.heigvd.iict.daa.template.entities.Note
import ch.heigvd.iict.daa.template.entities.NoteAndSchedule
import ch.heigvd.iict.daa.template.entities.*
import ch.heigvd.iict.daa.template.repository.NoteRepository
import java.util.Calendar

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: MutableLiveData<List<NoteAndSchedule>> = MutableLiveData()

    private fun generateInitialNotes() {
        if (allNotes.value.isNullOrEmpty()) {
            for (i in 1..10) {
                val newNote = Note.generateRandomNote()
                repository.insert(newNote)
            }
        }
    }

    fun sortByCreationDate() {
        allNotes.value = allNotes.value?.sortedBy { it.note.creationDate }
    }

    fun sortBySchedule() {
        allNotes.value = allNotes.value?.sortedBy { it.schedule?.date }
    }

    fun insert(note: Note) {
        repository.insert(note)
    }

    fun deleteAllNotes() {
        repository.deleteAllNotes()
    }
}