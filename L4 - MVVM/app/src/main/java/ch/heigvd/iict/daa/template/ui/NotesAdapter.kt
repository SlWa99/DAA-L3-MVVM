/**
 * Nom du fichier : NotesAdapter.kt
 * Description    : Adaptateur pour l'affichage des notes dans un RecyclerView.
 *                  Gère l'affichage des notes et de leurs échéances dans des éléments de type View.
 * Auteur         : Bugna, Slimani & Steiner
 * Date           : 22 novembre 2024
 */
package ch.heigvd.iict.daa.template.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.entities.*
import java.util.*

/**
 * Adaptateur pour l'affichage des notes dans un RecyclerView.
 * Gère l'affichage des notes et de leurs échéances dans des éléments de type View.
 */
class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var items = listOf<NoteAndSchedule>()
    val SCHEDULE_VIEW_TYPE = 1
    val NO_SCHEDULE_VIEW_TYPE = 0

    /**
     * ViewHolder personnalisé pour chaque élément de la liste.
     * Permet de lier les données d'un objet NoteAndSchedule aux vues correspondantes.
     */
    // ViewHolder pour chaque élément de la liste
    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.note_title)
        private val description: TextView = view.findViewById(R.id.note_description)
        private val typeIcon: ImageView = view.findViewById(R.id.note_type_icon)
        private val scheduleIcon: ImageView? = view.findViewById(R.id.note_schedule_icon)
        private val scheduleDate: TextView? = view.findViewById(R.id.schedule_text)

        /**
         * Lie les données d'un NoteAndSchedule aux vues.
         * Gère les différents types d'affichage (avec ou sans échéance).
         *
         * @param noteAndSchedule L'objet NoteAndSchedule à afficher.
         * @param viewType Le type de vue déterminé par getItemViewType.
         */
        fun bind(noteAndSchedule: NoteAndSchedule, viewType: Int) {
            val note = noteAndSchedule.note
            val schedule = noteAndSchedule.schedule

            // Définir le titre et la description
            title.text = note.title
            description.text = note.text

            // Définir l'icône en fonction du type de note
            typeIcon.setImageResource(
                when (note.type) {
                    Type.WORK -> R.drawable.work
                    Type.SHOPPING -> R.drawable.shopping
                    Type.FAMILY -> R.drawable.family
                    Type.TODO -> R.drawable.todo
                    else -> R.drawable.note // Par défaut
                }
            )

            // Changer la couleur de l’icône en fonction de l’état
            typeIcon.setColorFilter(
                ContextCompat.getColor(
                    itemView.context,
                    when (note.state) {
                        State.DONE -> R.color.green // Couleur pour "Terminé"
                        State.IN_PROGRESS -> R.color.black // Couleur pour "En cours"
                    }
                )
            )

            // Gestion de l'échéance (schedule)
            if (viewType == SCHEDULE_VIEW_TYPE && schedule != null) {
                scheduleIcon?.visibility = View.VISIBLE
                scheduleDate?.visibility = View.VISIBLE

                val remainingTimeInMillis =
                    schedule.date.timeInMillis - Calendar.getInstance().timeInMillis
                val daysLeft = (remainingTimeInMillis / (R.integer.millis_in_a_day)).toInt()

                scheduleDate?.text = when {
                    daysLeft < 0 -> {
                        // Si la date est dépassée
                        scheduleIcon?.setColorFilter(
                            ContextCompat.getColor(itemView.context, R.color.red)
                        )
                        "Late"
                    }
                    daysLeft < R.integer.day_in_mounth -> {
                        // Si la date est dans les 30 jours
                        scheduleIcon?.setColorFilter(
                            ContextCompat.getColor(itemView.context, R.color.black)
                        )
                        "$daysLeft days"
                    }
                    else -> {
                        // Si la date est à plus de 30 jours
                        scheduleIcon?.setColorFilter(
                            ContextCompat.getColor(itemView.context, R.color.black)
                        )
                        "${daysLeft / R.integer.day_in_mounth} months"
                    }
                }
            } else {
                // Masquer les éléments liés au schedule si non applicable
                scheduleIcon?.visibility = View.GONE
                scheduleDate?.visibility = View.GONE
            }
        }
    }

    /**
     * Crée un nouveau ViewHolder pour un élément de la liste.
     * Choisit la disposition en fonction du type de vue.
     *
     * @param parent Le ViewGroup parent.
     * @param viewType Le type de vue (avec ou sans échéance).
     * @return Un NoteViewHolder contenant la vue correspondante.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layout = if (viewType == SCHEDULE_VIEW_TYPE) {
            R.layout.fragment_notes_schedule
        } else {
            R.layout.fragment_notes_list
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return NoteViewHolder(view)
    }

    /**
     * Renvoie le nombre total d'éléments à afficher dans la liste.
     *
     * @return Le nombre d'éléments.
     */
    override fun getItemCount(): Int = items.size

    /**
     * Détermine le type de vue à utiliser pour un élément donné.
     * Les éléments avec une échéance reçoivent un type spécial.
     *
     * @param position La position de l'élément dans la liste.
     * @return Le type de vue (SCHEDULE_VIEW_TYPE ou NO_SCHEDULE_VIEW_TYPE).
     */
    override fun getItemViewType(position: Int): Int {
        println("Note at position $position has schedule: ${items[position].schedule != null}")
        return if (items[position].schedule != null) SCHEDULE_VIEW_TYPE else NO_SCHEDULE_VIEW_TYPE
    }

    /**
     * Lie les données d'un élément à un ViewHolder.
     *
     * @param holder Le ViewHolder à mettre à jour.
     * @param position La position de l'élément dans la liste.
     */
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(items[position], getItemViewType(position))
    }

    /**
     * Met à jour la liste des éléments affichés et notifie l'adaptateur.
     *
     * @param newItems La nouvelle liste d'éléments.
     */
    fun updateItems(newItems: List<NoteAndSchedule>) {
        items = newItems
        notifyDataSetChanged()
    }

    /**
     * Trie les éléments par date de création des notes.
     * Met à jour l'affichage après le tri.
     */
    fun sortByCreationDate() {
        items = items.sortedBy { it.note.creationDate }
        notifyDataSetChanged()
    }

    /**
     * Trie les éléments par date d'échéance des notes.
     * Les notes sans échéance sont affichées en dernier.
     * Met à jour l'affichage après le tri.
     */
    fun sortBySchedule() {
        items = items.sortedWith(compareBy(
            { it.schedule == null }, // Les éléments sans échéance vont à la fin
            { it.schedule?.date }   // Tri par date d'échéance
        ))
        notifyDataSetChanged()
    }
}