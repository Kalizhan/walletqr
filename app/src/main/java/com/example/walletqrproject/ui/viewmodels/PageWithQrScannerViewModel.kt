package com.example.walletqrproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.walletqrproject.ui.data.AllInfoDatabase
import com.example.walletqrproject.ui.data.WebUrlRepository
import com.example.walletqrproject.ui.model.WebUrl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PageWithQrScannerViewModel(application: Application): AndroidViewModel(application) {

    val readAllWebUrls: LiveData<List<WebUrl>>
    private val repository: WebUrlRepository

    init {
        val webUrlDao = AllInfoDatabase.getDatabase(
            application
        ).webUrlDao()
        repository = WebUrlRepository(webUrlDao)
        readAllWebUrls = repository.getAllWebUrls
    }

    fun insertWebUrl(webUrl: WebUrl){
        viewModelScope.launch ( Dispatchers.IO){
            repository.insertWebUrl(webUrl)
        }
    }

    fun deleteWebUrl(webUrl: WebUrl){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteWebUrl(webUrl)
        }
    }

}