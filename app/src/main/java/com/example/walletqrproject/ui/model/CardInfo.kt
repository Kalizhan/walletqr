package com.example.walletqrproject.ui.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_credit_card_info")
data class CardInfo(
    var cardNumber: String,
    var expirationDate: String,
    var cvv: String,
    var postalCode: String,
    var countryCode: String,
    var mobileNumber: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
