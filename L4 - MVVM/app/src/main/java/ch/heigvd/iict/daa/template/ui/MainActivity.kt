/**
 * Nom du fichier : MainActivity.kt
 * Description    : Activité principale de l'application qui gère l'interface utilisateur
 *                  et les interactions.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
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

/**
 * Activity principale de l'application qui gère l'interface utilisateur
 * et la navigation des fragments.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var notesFragment: NotesFragment

    private val noteViewModel: NoteViewModel by viewModels {
        val noteDao = AppDatabase.getDatabase(application).noteDao()
        val noteRepository = NoteRepository(noteDao)
        NoteViewModelFactory(noteRepository)
    }

    /**
     * Méthode appelée lors de la création de l'activité.
     * Configure l'interface utilisateur, la barre d'outils, et initialise le fragment NotesFragment.
     * Si un état précédent existe, il récupère le fragment existant.
     */
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

    /**
     * Méthode pour initialiser le menu de l'activité.
     * Cette méthode charge le menu principal défini dans les ressources XML.
     *
     * @param menu Le menu à charger.
     * @return `true` pour indiquer que le menu a été créé avec succès.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Méthode appelée lorsqu'un élément du menu est sélectionné.
     * Gère les différentes actions possibles : trier les notes par date d'ajout ou par date prévue,
     *                                          ajouter une note, ou supprimer toutes les notes.
     *
     * @param item L'élément de menu sélectionné.
     * @return `true` si l'action est traitée, sinon propage l'action au parent.
     */
    //TODO --> les deux premiers
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val sortedChoice = getString(R.string.sorted_choice)

        return when (item.itemId) {
            R.id.action_sort_by_creation -> {
                noteViewModel._sortedNotes.postValue(NoteViewModel.SortType.BY_DATE)
                getPreferences(Context.MODE_PRIVATE).edit().putString(sortedChoice, getString(R.string.creation_date)).apply()
                true
            }
            R.id.action_sort_by_schedule -> {
                noteViewModel._sortedNotes.postValue(NoteViewModel.SortType.BY_SCHEDULE)
                getPreferences(Context.MODE_PRIVATE).edit().putString(sortedChoice, getString(R.string.schedule)).apply()
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