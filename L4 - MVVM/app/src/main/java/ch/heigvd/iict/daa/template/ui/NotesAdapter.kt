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
import ch.heigvd.iict.daa.template.entities.State
import ch.heigvd.iict.daa.template.entities.Type
import java.text.SimpleDateFormat
import java.util.*

class NotesAdapter : ListAdapter<Note, NotesAdapter.NoteViewHolder>(NoteDiffCallback()) {


    private val dateFormatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.note_title)
        val description: TextView = view.findViewById(R.id.note_description)
        val typeIcon: ImageView = view.findViewById(R.id.note_type_icon)
        val scheduleIcon: ImageView = view.findViewById(R.id.note_schedule_icon)
        val scheduleDate: TextView = view.findViewById(R.id.note_schedule_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }


    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)

        // Set title and description
        holder.title.text = note.title
        holder.description.text = note.text

        // Set icon based on note type
        holder.typeIcon.setImageResource(
            when (note.type) {
                Type.WORK -> R.drawable.work
                Type.SHOPPING -> R.drawable.shopping
                Type.FAMILY -> R.drawable.family
                Type.TODO -> R.drawable.todo
                else -> R.drawable.note
            }
        )

        // Set color based on note type
        holder.typeIcon.setColorFilter(
            ContextCompat.getColor(
                holder.itemView.context,
                when (note.state) {
                    State.DONE -> R.color.green
                    State.IN_PROGRESS -> R.color.black
                }
            )
        )

        // Schedule icon and date display logic
        val schedule = Note.generateRandomSchedule()
        if (schedule != null) {
            holder.scheduleIcon.visibility = View.VISIBLE
            holder.scheduleDate.visibility = View.VISIBLE
            val diffInMillis = schedule.date.timeInMillis - Calendar.getInstance().timeInMillis
            val daysLeft = (diffInMillis / (1000 * 60 * 60 * 24)).toInt()
            holder.scheduleDate.text = when {
                daysLeft < 0 -> {
                    holder.scheduleIcon.setColorFilter(
                        ContextCompat.getColor(holder.itemView.context, R.color.red)
                    )
                    "Late"
                }
                daysLeft < 30 -> "$daysLeft days"
                else -> "${daysLeft / 30} months"
            }
        } else {
            holder.scheduleIcon.visibility = View.GONE
            holder.scheduleDate.visibility = View.GONE
        }

    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.noteId == newItem.noteId
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}