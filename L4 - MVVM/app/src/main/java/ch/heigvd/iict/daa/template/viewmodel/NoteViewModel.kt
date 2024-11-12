package ch.heigvd.iict.daa.template.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.heigvd.iict.daa.template.entities.Note
import ch.heigvd.iict.daa.template.entities.NoteAndSchedule
import ch.heigvd.iict.daa.template.entities.*
import ch.heigvd.iict.daa.template.repository.NoteRepository
import kotlinx.coroutines.launch
import java.util.Calendar


class NoteViewModel(private val repository: NoteRepository) : ViewModel() {
    val allNotes: LiveData<List<NoteAndSchedule>> = repository.allNotes

    fun generateANote() {
        viewModelScope.launch {
            repository.generateANote()
        }
    }
/**
    fun sortByCreationDate() {
        allNotes.value = allNotes.value?.sortedBy { it.note.creationDate }
    }

    fun sortBySchedule() {
        allNotes.value = allNotes.value?.sortedBy { it.schedule?.date }
    }*/

    fun insert(note: NoteAndSchedule) {
        viewModelScope.launch {
            repository.insert(note)
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }
}