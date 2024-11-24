/**
 * Nom du fichier : NoteDao.kt
 * Description    : Interface DAO pour effectuer des opérations sur les entités Note et Schedule
 *                  dans la base de données. Contient des méthodes pour insérer, supprimer et
 *                  récupérer des notes.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.heigvd.iict.daa.template.entities.*

/**
 * Interface DAO pour effectuer des opérations sur les entités Note
 * et Schedule dans la base de données.
 * Contient des méthodes pour insérer, supprimer et récupérer des notes et des programmes.
 */
@Dao
interface NoteDao {
    /**
     * Insère une note dans la base de données.
     *
     * @param note L'entité Note à insérer.
     * @return L'identifiant généré pour la note insérée.
     */
    @Insert
    fun insertNote(note: Note) : Long

    /**
     * Insère ou met à jour une note dans la base de données.
     *
     * @param note L'entité Note à insérer ou mettre à jour.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    /**
     * Insère un programme (Schedule) dans la base de données.
     *
     * @param schedule L'entité Schedule à insérer.
     * @return L'identifiant généré pour le programme inséré.
     */
    @Insert
    fun insertSchedule(schedule: Schedule): Long

    /**
     * Supprime tous les programmes (Schedule) de la base de données.
     */
    @Query("DELETE FROM Schedule")
    fun deleteAllSchedules()

    /**
     * Récupère toutes les notes et leurs programmes associés.
     *
     * @return Une liste réactive (LiveData) de NoteAndSchedule.
     */
    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    /**
     * Supprime toutes les notes de la base de données.
     */
    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    /**
     * Récupère le nombre total de notes dans la base de données.
     * @return Une valeur réactive (LiveData) représentant le nombre de notes.
     */
    @Query("SELECT COUNT(*) FROM Note")
    fun getCount() : LiveData<Long>
}