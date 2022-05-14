package com.example.walletqrproject.ui.data

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.walletqrproject.ui.model.CardInfo

@Dao
interface CardInfoDao {

    @Query("SELECT * FROM table_credit_card_info ORDER BY id ASC")
    fun getAll(): LiveData<List<CardInfo>>

    @Query("SELECT * FROM table_credit_card_info WHERE id=:id")
    fun getById(id: Int): LiveData<List<CardInfo>>

    @Insert
    suspend fun insert(cardInfo: CardInfo)

    @Delete
    suspend fun delete(cardInfo: CardInfo)
}