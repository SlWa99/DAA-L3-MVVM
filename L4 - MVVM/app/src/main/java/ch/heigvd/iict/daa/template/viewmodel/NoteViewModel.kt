package ch.heigvd.iict.daa.template.viewmodel

import androidx.lifecycle.*
import ch.heigvd.iict.daa.template.entities.Note
import ch.heigvd.iict.daa.template.entities.NoteAndSchedule
import ch.heigvd.iict.daa.template.entities.*
import ch.heigvd.iict.daa.template.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private var currentSortType = SortType.NONE

    private val _sortedNotes = MediatorLiveData<List<NoteAndSchedule>>()
    val sortedNotes: LiveData<List<NoteAndSchedule>> = _sortedNotes

    init {
        _sortedNotes.addSource(repository.allNotes) { notes ->
            _sortedNotes.value = when(currentSortType) {
                SortType.BY_DATE -> notes.sortedBy { it.note.creationDate }
                SortType.BY_SCHEDULE -> notes.sortedBy { it.schedule?.date }
                SortType.NONE -> notes
            }
        }
    }

    fun generateANote() {
        viewModelScope.launch {
            repository.generateANote()
        }
    }

    fun sortByCreationDate() {
        currentSortType = SortType.BY_DATE
        _sortedNotes.value = _sortedNotes.value?.sortedBy { it.note.creationDate }
    }

    fun sortBySchedule() {
        currentSortType = SortType.BY_SCHEDULE
        _sortedNotes.value = _sortedNotes.value?.sortedBy { it.schedule?.date }
    }

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

    private enum class SortType {
        NONE,
        BY_DATE,
        BY_SCHEDULE
    }
}