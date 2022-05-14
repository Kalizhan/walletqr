package com.example.walletqrproject.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "money_data")
data class MoneyData(
    var newMoney: Int,
    var inorout: String,
    var date: String,
    var initialMoney: Int?,
    var category: String,
    var account: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
