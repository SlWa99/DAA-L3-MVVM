package ch.heigvd.iict.daa.template.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.data.AppDatabase
import ch.heigvd.iict.daa.template.repository.NoteRepository
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private val noteViewModel: NoteViewModel by viewModels {
        val noteDao = AppDatabase.getDatabase(application).noteDao()
        val noteRepository = NoteRepository(noteDao)
        NoteViewModelFactory(noteRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NotesFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_creation -> {
                noteViewModel.sortByCreationDate()
                true
            }
            R.id.action_sort_by_schedule -> {
                noteViewModel.sortBySchedule()
                true
            }
            R.id.action_add_note -> {
                noteViewModel.generateANote()
                true
            }
            R.id.action_delete_all -> {
                noteViewModel.deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}