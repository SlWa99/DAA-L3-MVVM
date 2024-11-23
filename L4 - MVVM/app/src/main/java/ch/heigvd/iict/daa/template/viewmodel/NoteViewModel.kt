package ch.heigvd.iict.daa.template.viewmodel

import android.util.Log
import androidx.lifecycle.*
import ch.heigvd.iict.daa.template.entities.Note
import ch.heigvd.iict.daa.template.entities.NoteAndSchedule
import ch.heigvd.iict.daa.template.entities.*
import ch.heigvd.iict.daa.template.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    private var currentSortType = SortType.NONE
    val allNotes = repository.allNotes
    val notesCount = repository.notesCount

    private val _sortedNotes = MediatorLiveData<List<NoteAndSchedule>>()
    val sortedNotes: LiveData<List<NoteAndSchedule>> = _sortedNotes

    init {
        _sortedNotes.addSource(repository.allNotes) { notes ->
            _sortedNotes.value = when(currentSortType) {
                SortType.BY_DATE -> notes.sortedBy { it.note.creationDate }
                SortType.BY_SCHEDULE -> notes.sortedBy { it.schedule?.date }
                SortType.NONE -> notes
            }
            Log.d("DEBUG", "init notes : " + notes)
        }
        Log.d("DEBUG", "init _sortedNotes : " + _sortedNotes.value)
        Log.d("DEBUG", "init allNotes : " + allNotes)
        Log.d("DEBUG", "init allNotes.value : " + allNotes.value)
    }

    fun generateANote() {
        viewModelScope.launch {
            repository.generateANote()
        }
    }

    fun sortByCreationDate() {
        Log.d("DEBUG", "BY_DATE")
        Log.d("DEBUG", _sortedNotes.toString())
        Log.d("DEBUG", _sortedNotes.value?.size.toString())
        Log.d("DEBUG", _sortedNotes.value.toString())
        Log.d("DEBUG", "sortByCreationDate allNotes : " + allNotes)
        Log.d("DEBUG", "sortByCreationDate allNotes.value : " + allNotes.value)
        Log.d("DEBUG", "sortByCreationDate repository.allNotes.value : " + repository.allNotes.value)


        currentSortType = SortType.BY_DATE
        _sortedNotes.value = _sortedNotes.value?.sortedBy { it.note.creationDate }
    }

    fun sortBySchedule() {
        Log.d("DEBUG", "BY_SCHEDULE")
        Log.d("DEBUG", _sortedNotes.toString())
        Log.d("DEBUG", _sortedNotes.value?.size.toString())
        Log.d("DEBUG", _sortedNotes.value.toString())

        currentSortType = SortType.BY_SCHEDULE
        _sortedNotes.value = _sortedNotes.value?.sortedBy { it.schedule?.date }
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