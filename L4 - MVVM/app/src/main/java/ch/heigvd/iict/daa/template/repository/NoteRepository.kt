package ch.heigvd.iict.daa.template.repository

import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.template.data.NoteDao
import ch.heigvd.iict.daa.template.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<NoteAndSchedule>> = noteDao.getAllNotes()
    val notesCount = noteDao.getCount()

    suspend fun insert(note: NoteAndSchedule) {
        withContext(Dispatchers.IO) {
            noteDao.insert(note.note)
        }
    }

    suspend fun deleteAllNotes() {
        withContext(Dispatchers.IO) {
            noteDao.deleteAllNotes()
        }
    }

    suspend fun updateSchedule(schedule: Schedule) {
        withContext(Dispatchers.IO) {
            noteDao.updateSchedule(schedule)
        }
    }

    suspend fun generateANote() {
        insert(NoteAndSchedule(Note.generateRandomNote(), Note.generateRandomSchedule()))
    }
}