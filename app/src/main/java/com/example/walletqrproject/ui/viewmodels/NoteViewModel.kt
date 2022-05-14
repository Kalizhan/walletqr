package com.example.walletqrproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.walletqrproject.ui.data.AllInfoDatabase
import com.example.walletqrproject.ui.data.NoteRepository
import com.example.walletqrproject.ui.model.MoneyData
import com.example.walletqrproject.ui.model.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application): AndroidViewModel(application) {

    val readAllNotes: LiveData<List<Note>>
    val noteList = arrayListOf<Note>()
    private val repository: NoteRepository

    init {
        val noteDao = AllInfoDatabase.getDatabase(
            application
        ).noteDao()
        repository = NoteRepository(noteDao)
        readAllNotes = repository.getAllNotes
    }

    fun insertNote(note: Note){
        viewModelScope.launch(Dispatchers.IO){
            repository.insertNote(note)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteNote(note)
        }
    }

}