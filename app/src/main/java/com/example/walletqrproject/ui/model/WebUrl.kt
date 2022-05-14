package com.example.walletqrproject.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_web_url")
data class WebUrl(
    val url: String,
    val date: String
){
    @PrimaryKey(autoGenerate = true)
    var webId: Long = 0
}