package ch.heigvd.iict.daa.template.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
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

    private lateinit var notesFragment: NotesFragment

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

        // Ajouter dynamiquement le fragment NotesFragment dans le FrameLayout
        if (savedInstanceState == null) {
            notesFragment = NotesFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, notesFragment).commit()
        } else {
            // Récupérer une instance existante du fragment
            notesFragment = supportFragmentManager.findFragmentById(R.id.fragment_container) as NotesFragment
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    //TODO --> les deux premiers
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_creation -> {
                noteViewModel._sortedNotes.postValue(NoteViewModel.SortType.BY_DATE)
                getPreferences(Context.MODE_PRIVATE).edit().putString("sorted_choice", "CreationDate").apply()
                true
            }
            R.id.action_sort_by_schedule -> {
                noteViewModel._sortedNotes.postValue(NoteViewModel.SortType.BY_SCHEDULE)
                getPreferences(Context.MODE_PRIVATE).edit().putString("sorted_choice", "Schedule").apply()
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