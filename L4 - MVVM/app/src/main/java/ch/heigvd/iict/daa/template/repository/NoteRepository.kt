package ch.heigvd.iict.daa.template.repository

import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.template.data.NoteDao
import ch.heigvd.iict.daa.template.entities.*

class NoteRepository(private val noteDao: NoteDao) {
    val allNotes: LiveData<List<NoteAndSchedule>> = noteDao.getAllNotes()

    fun insert(note: Note) {
        noteDao.insert(note)
    }

    fun deleteAllNotes() {
        noteDao.deleteAllNotes()
    }

    // Nouvelle méthode pour mettre à jour le Schedule
    fun updateSchedule(schedule: Schedule) {
        noteDao.updateSchedule(schedule)
    }
}