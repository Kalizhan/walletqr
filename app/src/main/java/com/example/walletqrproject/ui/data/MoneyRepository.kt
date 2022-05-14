package com.example.walletqrproject.ui.data

import androidx.lifecycle.LiveData
import com.example.walletqrproject.ui.model.MoneyData
import kotlinx.coroutines.flow.Flow

class MoneyRepository(private val moneyDataDao: MoneyDataDao) {

    val getAll: LiveData<List<MoneyData>> = moneyDataDao.getAll()

//    val getInitialMoney: LiveData<Int> = moneyDataDao.getQuantityOfMoney()

    suspend fun insert(moneyData: MoneyData){
        moneyDataDao.insert(moneyData)
    }

    suspend fun delete(moneyData: MoneyData){
        moneyDataDao.delete(moneyData)
    }

    fun getAccountInfo(account: String): LiveData<Int> {
        return moneyDataDao.getAccountInfo(account)
    }

}