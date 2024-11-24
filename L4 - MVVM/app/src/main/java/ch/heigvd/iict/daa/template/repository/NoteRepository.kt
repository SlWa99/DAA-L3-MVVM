/**
 * Nom du fichier : NoteRepository.kt
 * Description    : Représente un repository pour gérer les données liées aux notes
 *                  et à leurs programmes. Permet d'effectuer des opérations telles que l'insertion,
 *                  la suppression et la récupération des notes.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.repository

import androidx.lifecycle.LiveData
import ch.heigvd.iict.daa.template.data.NoteDao
import ch.heigvd.iict.daa.template.entities.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Représente un repository pour gérer les données liées aux notes et à leurs programmes.
 * Permet d'effectuer des opérations telles que l'insertion, la suppression
 * et la récupération des notes.
 */
class NoteRepository(private val noteDao: NoteDao) {

    val allNotes: LiveData<List<NoteAndSchedule>> = noteDao.getAllNotes()
    val notesCount = noteDao.getCount()

    /**
     * Insère une note et, si applicable, son programme associé dans la base de données.
     *
     * @param note Une instance de [NoteAndSchedule] représentant une note
     * et éventuellement un programme associé.
     */
    private suspend fun insert(note: NoteAndSchedule) {
        withContext(Dispatchers.IO) {
            // 1. Insérer la note et récupérer son ID
            val noteId = noteDao.insertNote(note.note)  // Utiliser insertNote au lieu de insert

            // 2. Si un schedule existe, l'insérer avec l'ID de la note
            note.schedule?.let { schedule ->
                schedule.ownerId = noteId  // Définir l'ownerId avec l'ID de la note
                noteDao.insertSchedule(schedule)
            }
        }
    }

    /**
     * Supprime toutes les notes présentes dans la base de données.
     */
    suspend fun deleteAllNotes() {
        withContext(Dispatchers.IO) {
            noteDao.deleteAllNotes()
        }
    }

    /**
     * Génère et insère une note aléatoire accompagnée d'un programme aléatoire.
     */
    suspend fun generateANote() {
        insert(NoteAndSchedule(Note.generateRandomNote(), Note.generateRandomSchedule()))
    }
}