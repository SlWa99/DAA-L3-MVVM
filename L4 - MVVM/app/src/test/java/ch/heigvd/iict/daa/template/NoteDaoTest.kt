/**
 * Nom du fichier : NoteDaoTest.kt
 * Description    : Classe de test unitaire pour le DAO de la base de données des notes.
 *                  Permet de tester les opérations d'insertion et de récupération des notes.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import ch.heigvd.iict.daa.template.data.AppDatabase
import ch.heigvd.iict.daa.template.data.NoteDao
import ch.heigvd.iict.daa.template.entities.Note
import ch.heigvd.iict.daa.template.entities.State
import ch.heigvd.iict.daa.template.entities.Type
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.runner.RunWith
import java.util.Calendar
import org.junit.runners.JUnit4

/**
 * Classe de test unitaire pour le DAO de la base de données des notes.
 * Permet de tester les opérations d'insertion et de récupération des notes.
 */
@RunWith(JUnit4::class)
class NoteDaoTest {

    // DB utilisée pour les tests (en mémoire).
    private lateinit var database: AppDatabase

    // DAO utilisé pour interagir avec la DB pendant les tests.
    private lateinit var noteDao: NoteDao

    /**
     * Configure la base de données et initialise le DAO avant chaque test.
     */
    @Before
    fun setUp() {
        // Utilise une base de données en mémoire pour les tests
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = database.noteDao()
    }

    /**
     * Ferme la base de données après chaque test pour libérer les ressources.
     */
    @After
    fun tearDown() {
        database.close()
    }

    /**
     * Vérifie qu'une note peut être insérée dans la base de données
     * et récupérée correctement.
     */
    @Test
    fun insertAndRetrieveNote() = runBlocking {
        val note = Note(
            noteId = null,
            state = State.IN_PROGRESS,
            title = "Test Note",
            text = "This is a test note",
            creationDate = Calendar.getInstance(),
            type = Type.WORK
        )

        // Insère la note dans la base de données
        noteDao.insert(note)

        // Récupère toutes les notes et vérifie que l'insertion a fonctionné
        val notes = noteDao.getAllNotes().value
        assertNotNull(notes)
        assertEquals(1, notes?.size)
        assertEquals("Test Note", notes?.get(0)?.note?.title)
    }
}