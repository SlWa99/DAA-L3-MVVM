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

@RunWith(JUnit4::class)
class NoteDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var noteDao: NoteDao

    @Before
    fun setUp() {
        // Utilise une base de données en mémoire pour les tests
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        noteDao = database.noteDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

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