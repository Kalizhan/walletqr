package com.example.walletqrproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.walletqrproject.ui.data.AllInfoDatabase
import com.example.walletqrproject.ui.data.MoneyRepository
import com.example.walletqrproject.ui.model.MoneyData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivitySaveMoneyViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    val readAllData: LiveData<List<MoneyData>>
    private val repository: MoneyRepository
//    val getInitialMoney: LiveData<Int>
//    val readAllCard2: LiveData<List<MoneyData>>
//    val getInitalCard1: LiveData<Int>
//    val readAllCard3: LiveData<List<MoneyData>>
//    val getInitalCard2: LiveData<Int>


    init {
        val courseDao = AllInfoDatabase.getDatabase(application).moneyDataDao()

        repository = MoneyRepository(courseDao!!)

        readAllData = repository.getAll
//        readAllCard2 = repositoryCard1.getAll
//        readAllCard3 = repositoryCard2.getAll
//        getInitialMoney = repository.getInitialMoney
//        getInitalCard1 = repositoryCard1.getInitialMoney
//        getInitalCard2 = repositoryCard2.getInitialMoney



    }

    fun insertMoney(moneyData: MoneyData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(moneyData)
        }
    }

    fun getAccountInfo(account: String): LiveData<Int>{
        return repository.getAccountInfo(account)
    }

//    fun insertMoneyCard1(moneyData: MoneyData) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repositoryCard1.insert(moneyData)
//        }
//    }
//
//    fun insertMoneyCard2(moneyData: MoneyData) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repositoryCard2.insert(moneyData)
//        }
//    }

    fun deleteMoneyData(moneyData: MoneyData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(moneyData)
        }
    }

//    fun deleteMoneyCard1(moneyData: MoneyData) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repositoryCard1.delete(moneyData)
//        }
//    }
//
//    fun deleteMoneyCard2(moneyData: MoneyData) {
//        viewModelScope.launch(Dispatchers.IO) {
//            repositoryCard2.delete(moneyData)
//        }
//    }
}