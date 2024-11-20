import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.entities.Note
import ch.heigvd.iict.daa.template.entities.NoteAndSchedule
import ch.heigvd.iict.daa.template.entities.State
import ch.heigvd.iict.daa.template.entities.Type
import java.text.SimpleDateFormat
import java.util.*


class NotesAdapter : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    var items = listOf<NoteAndSchedule>()

    companion object {
        const val SCHEDULE_VIEW_TYPE = 1
        const val NO_SCHEDULE_VIEW_TYPE = 0
    }

    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    // ViewHolder pour chaque élément de la liste
    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val title: TextView = view.findViewById(R.id.note_title)
        private val description: TextView = view.findViewById(R.id.note_description)
        private val typeIcon: ImageView = view.findViewById(R.id.note_type_icon)
        private val scheduleIcon: ImageView? = view.findViewById(R.id.note_schedule_icon)
        private val scheduleDate: TextView? = view.findViewById(R.id.schedule_text)

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

                val remainingTimeInMillis = schedule.date.timeInMillis - Calendar.getInstance().timeInMillis
                val daysLeft = (remainingTimeInMillis / (1000 * 60 * 60 * 24)).toInt()

                scheduleDate?.text = when {
                    daysLeft < 0 -> {
                        // Si la date est dépassée
                        scheduleIcon?.setColorFilter(
                            ContextCompat.getColor(itemView.context, R.color.red)
                        )
                        "Late"
                    }
                    daysLeft < 30 -> {
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
                        "${daysLeft / 30} months"
                    }
                }
            } else {
                // Masquer les éléments liés au schedule si non applicable
                scheduleIcon?.visibility = View.GONE
                scheduleDate?.visibility = View.GONE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val layout = if (viewType == SCHEDULE_VIEW_TYPE) {
            R.layout.fragment_notes_schedule
        } else {
            R.layout.fragment_notes_list
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        println("Note at position $position has schedule: ${items[position].schedule != null}")
        return if (items[position].schedule != null) SCHEDULE_VIEW_TYPE else NO_SCHEDULE_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(items[position], getItemViewType(position))
    }

    // Met à jour les éléments affichés
    fun updateItems(newItems: List<NoteAndSchedule>) {
        items = newItems
        notifyDataSetChanged()
    }

    // Tri par date de création
    fun sortByCreationDate() {
        items = items.sortedBy { it.note.creationDate }
        notifyDataSetChanged()
    }

    // Tri par date d'échéance
    fun sortBySchedule() {
        items = items.sortedWith(compareBy(
            { it.schedule == null }, // Les éléments sans échéance vont à la fin
            { it.schedule?.date }   // Tri par date d'échéance
        ))
        notifyDataSetChanged()
    }
}