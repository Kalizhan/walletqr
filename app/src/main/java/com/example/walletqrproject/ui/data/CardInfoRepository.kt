package com.example.walletqrproject.ui.data

import androidx.lifecycle.LiveData
import com.example.walletqrproject.ui.model.CardInfo

class CardInfoRepository(private val cardInfoDao: CardInfoDao) {

    val getAll: LiveData<List<CardInfo>> = cardInfoDao.getAll()

    fun getById(id: Int):LiveData<List<CardInfo>>{
        return cardInfoDao.getById(id)
    }
//    val getById: LiveData<List<CardInfo>> = cardInfoDao.getById(cardInfo)

    suspend fun insert(cardInfo: CardInfo){
        cardInfoDao.insert(cardInfo)
    }

    suspend fun delete(cardInfo: CardInfo){
        cardInfoDao.delete(cardInfo)
    }
}