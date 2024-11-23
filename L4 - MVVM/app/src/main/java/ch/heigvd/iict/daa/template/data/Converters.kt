/**
 * Nom du fichier : Converters.kt
 * Description    : Fournit des méthodes de conversion pour manipuler les types complexes
 *                  (comme Calendar) dans la base de données Room.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.data

import androidx.room.TypeConverter
import java.util.Calendar

/**
 * Fournit des méthodes de conversion pour manipuler les types complexes (comme Calendar)
 * dans la base de données Room.
 */
class Converters {
    /**
     * Convertit un timestamp en un objet [Calendar].
     * Utilisé par Room pour stocker un type complexe non supporté directement (Calendar).
     *
     * @param value Un timestamp en millisecondes (type Long) ou null.
     * @return Un objet [Calendar] initialisé avec le timestamp, ou null si l'entrée est null.
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Calendar? {
        return value?.let {
            Calendar.getInstance().apply { timeInMillis = it }
        }
    }

    /**
     * Convertit un objet [Calendar] en un timestamp (Long).
     * Utilisé par Room pour enregistrer les données dans la base sous forme de valeur primitive.
     *
     * @param calendar Un objet [Calendar] ou null.
     * @return Un timestamp en millisecondes (type Long) correspondant à la date/heure du Calendar,
     * ou null si l'entrée est null.
     */
    @TypeConverter
    fun calendarToTimestamp(calendar: Calendar?): Long? {
        return calendar?.timeInMillis
    }
}