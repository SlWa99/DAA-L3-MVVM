/**
 * Nom du fichier : NoteViewModel.kt
 * Description    : ViewModel qui gère les données de l'interface utilisateur liées aux notes.
 *                  Permet de manipuler les données de manière réactive
 *                  et d'effectuer des actions sur le repository.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.viewmodel

import androidx.lifecycle.*
import ch.heigvd.iict.daa.template.repository.NoteRepository
import kotlinx.coroutines.launch

/**
 * ViewModel qui gère les données de l'interface utilisateur liées aux notes.
 * Permet de manipuler les données de manière réactive et d'effectuer des actions sur le repository.
 */
class NoteViewModel(private val repository: NoteRepository) : ViewModel() {

    val allNotes = repository.allNotes
    val countNotes = repository.notesCount

    val sortedNotes = MutableLiveData(SortType.BY_DATE)

    /**
     * Génère une nouvelle note aléatoire via le repository.
     */
    fun generateANote() {
        viewModelScope.launch {
            repository.generateANote()
        }
    }

    /**
     * Supprime toutes les notes de la base de données via le repository.
     */
    fun deleteAllNotes() {
        viewModelScope.launch {
            repository.deleteAllNotes()
        }
    }

    /**
     * Enumération des types de tri possibles pour les notes.
     */
    enum class SortType {
        NONE,
        BY_DATE,
        BY_SCHEDULE
    }
}