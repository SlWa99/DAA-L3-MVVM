package ch.heigvd.iict.daa.template.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.entities.*
import ch.heigvd.iict.daa.template.viewmodel.NoteViewModel
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private val noteViewModel: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Lier la Toolbar à l'Activity comme ActionBar
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Charger NotesFragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, NotesFragment())
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate le menu, ce qui ajoute les options dans la Toolbar
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort_by_creation -> {
                // Implémente le tri par date de création
                noteViewModel.sortByCreationDate()
                true
            }
            R.id.action_sort_by_schedule -> {
                // Implémente le tri par échéance
                noteViewModel.sortBySchedule()
                true
            }
            R.id.action_add_note -> {
                // Génère une nouvelle note
                val newNote = Note.generateRandomNote() // Utiliser une méthode pour générer une note aléatoire
                noteViewModel.insert(newNote)
                true
            }
            R.id.action_delete_all -> {
                // Supprime toutes les notes
                noteViewModel.deleteAllNotes()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}