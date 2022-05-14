package com.example.walletqrproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.walletqrproject.ui.data.AllInfoDatabase
import com.example.walletqrproject.ui.data.CardInfoRepository
import com.example.walletqrproject.ui.model.CardInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShowCreditCardInfoViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<CardInfo>>
    private val repository: CardInfoRepository

    init {
        val cardInfoDao = AllInfoDatabase.getDatabase(
            application
        ).cardInfo()
        repository = CardInfoRepository(cardInfoDao)
        readAllData = repository.getAll
    }

    fun insertCard(cardInfo: CardInfo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(cardInfo)
        }
    }

    fun getById(id: Int): LiveData<List<CardInfo>>{
        return repository.getById(id)
    }

    fun deleteCard(cardInfo: CardInfo){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(cardInfo)
        }
    }

}