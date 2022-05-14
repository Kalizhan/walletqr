package com.example.walletqrproject.ui.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.walletqrproject.ui.model.*

@Database(
    entities = [MoneyData::class, Note::class, WebUrl::class, CardInfo::class],
    version = 18,
    exportSchema = false
)
abstract class AllInfoDatabase : RoomDatabase() {

    abstract fun moneyDataDao(): MoneyDataDao?
    abstract fun cardInfo(): CardInfoDao

    abstract fun noteDao(): NoteDao
    abstract fun webUrlDao(): WebUrlDao

    companion object {
        @Volatile
        private var INSTANCE: AllInfoDatabase? = null

        fun getDatabase(ctx: Context): AllInfoDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    ctx.applicationContext,
                    AllInfoDatabase::class.java,
                    "course_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}