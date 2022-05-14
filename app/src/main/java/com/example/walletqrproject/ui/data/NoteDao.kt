package com.example.walletqrproject.ui.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.walletqrproject.ui.model.Note

@Dao
interface NoteDao {

    @Query("SELECT * FROM table_note ORDER BY noteId ASC")
    fun getAllNotes(): LiveData<List<Note>>

    @Insert
    suspend fun insertNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

}