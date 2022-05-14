package com.example.walletqrproject.ui.data

import androidx.lifecycle.LiveData
import com.example.walletqrproject.ui.model.WebUrl

class WebUrlRepository(private val webUrlDao: WebUrlDao) {

    val getAllWebUrls: LiveData<List<WebUrl>> = webUrlDao.getAllWebUrls()

    suspend fun insertWebUrl(webUrl: WebUrl){
        webUrlDao.insertWebUrl(webUrl)
    }

    suspend fun deleteWebUrl(webUrl: WebUrl){
        webUrlDao.deleteWebUrl(webUrl)
    }
}