/**
 * Nom du fichier : AppDatabase.kt
 * Description    : Base de données Room pour gérer l'entité Note et l'entité Schedule.
 *                  Permet les opérations de lecture et d'écriture dans la base de données.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.entities.*

/**
 * Classe représentant la base de données de l'application,
 * utilisant Room pour la gestion des données locales.
 * Elle définit les entités et les DAO associés,
 * et fournit une instance singleton de la base de données.
 */
@Database(entities = [Note::class, Schedule::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        /**
         * Retourne une instance singleton de la base de données.
         * Crée la base de données si elle n'existe pas encore.
         *
         * @param context Le contexte d'application, nécessaire pour créer la base de données.
         * @return Une instance de [AppDatabase].
         */
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    context.getString(R.string.note_database)
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}