package com.example.goodnewsapplication.data.news

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [NewsEntity::class], version = 1, exportSchema = false)
abstract class NewsDataBase : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {

        @Volatile
        private var Instance: NewsDataBase? = null

        fun getDatabase(context: Context) : NewsDataBase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NewsDataBase::class.java, "news_database")
                    .createFromAsset("database/news.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}