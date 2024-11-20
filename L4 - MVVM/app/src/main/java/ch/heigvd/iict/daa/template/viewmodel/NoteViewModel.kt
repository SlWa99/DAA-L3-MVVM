package ch.heigvd.iict.daa.template.viewmodel

import androidx.lifecycle.*
import ch.heigvd.iict.daa.template.entities.Note
import ch.heigvd.iict.daa.template.entities.NoteAndSchedule
import ch.heigvd.iict.daa.template.entities.*
import ch.heigvd.iict.daa.template.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes = repository.allNotes
    val countNotes = repository.notesCount

    val _sortedNotes = MutableLiveData<SortType>(SortType.BY_DATE)

    fun generateANote() {
        viewModelScope.launch {
            repository.generateANote()
        }
    }

    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }

    enum class SortType {
        NONE,
        BY_DATE,
        BY_SCHEDULE
    }
}