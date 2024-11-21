package ch.heigvd.iict.daa.template.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.heigvd.iict.daa.template.entities.*

@Dao
interface NoteDao {
    @Insert
    fun insertNote(note: Note) : Long

    @Insert
    fun insertSchedule(schedule: Schedule): Long

    @Query("DELETE FROM Schedule")
    fun deleteAllSchedules()

    @Query("SELECT * FROM Note")
    fun getAllNotes(): LiveData<List<NoteAndSchedule>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Query("DELETE FROM Note")
    fun deleteAllNotes()

    @Query("SELECT COUNT(*) FROM Note")
    fun getCount() : LiveData<Long>
}