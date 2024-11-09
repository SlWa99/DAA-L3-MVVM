package ch.heigvd.iict.daa.template.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import ch.heigvd.iict.daa.template.R

class MainActivity : AppCompatActivity() {
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
                // Code pour trier par date de création
                true
            }
            R.id.action_sort_by_schedule -> {
                // Code pour trier par date prévue
                true
            }
            R.id.action_add_note -> {
                // Code pour ajouter une note aléatoire
                true
            }
            R.id.action_delete_all -> {
                // Code pour supprimer toutes les notes
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}