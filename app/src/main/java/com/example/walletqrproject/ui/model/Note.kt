package com.example.walletqrproject.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_note")
data class Note(
    var noteText: String,
    var noteDate: String
){
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0
}