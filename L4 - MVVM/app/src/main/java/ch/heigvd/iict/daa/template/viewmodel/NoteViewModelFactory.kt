/**
 * Nom du fichier : NoteViewModelFactory.kt
 * Description    : Factory pour créer des instances de NoteViewModel.
 *                  Permet de fournir un repository au ViewModel lors de sa création.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.test.core.app.ApplicationProvider
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.repository.NoteRepository

/**
 * Factory pour créer des instances de NoteViewModel.
 * Permet de fournir un repository au ViewModel lors de sa création.
 */
class NoteViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {
    /**
    * Crée une instance de ViewModel associée au repository.
    *
    * @param modelClass La classe du ViewModel à instancier.
    * @return Une instance de ViewModel de type [T].
    * @throws IllegalArgumentException Si la classe du ViewModel est inconnue.
    */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repository) as T
        }
        throw IllegalArgumentException(ApplicationProvider.getApplicationContext<Context>()
            .getString(R.string.error_unknown_viewmodel))
    }
}