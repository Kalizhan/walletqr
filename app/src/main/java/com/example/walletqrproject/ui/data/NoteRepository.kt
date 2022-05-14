package com.example.walletqrproject.ui.data

import androidx.lifecycle.LiveData
import com.example.walletqrproject.ui.model.Note

class NoteRepository(private val noteDao: NoteDao) {

    val getAllNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }
}