package com.example.walletqrproject.ui.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.walletqrproject.ui.model.WebUrl

@Dao
interface WebUrlDao {

    @Query("SELECT * FROM table_web_url ORDER BY webId ASC")
    fun getAllWebUrls(): LiveData<List<WebUrl>>

    @Insert
    suspend fun insertWebUrl(webUrl: WebUrl)

    @Delete
    suspend fun deleteWebUrl(webUrl: WebUrl)

}