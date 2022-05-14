package com.example.walletqrproject.ui.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.walletqrproject.ui.model.MoneyData
import kotlinx.coroutines.flow.Flow

@Dao
interface MoneyDataDao {

    @Query("SELECT * FROM money_data ORDER BY id ASC")
    fun getAll(): LiveData<List<MoneyData>>

//    @Query("SELECT initialMoney FROM money_data ORDER BY id DESC LIMIT 1")
//    fun getQuantityOfMoney(): LiveData<Int>

    @Query("SELECT initialMoney FROM money_data WHERE account=:account ORDER BY id DESC LIMIT 1")
    fun getAccountInfo(account: String): LiveData<Int>

    @Insert
    suspend fun insert(moneyData: MoneyData?)

    @Delete
    suspend fun delete(moneyData: MoneyData?)
}