package com.example.harrypottermagicdictionary.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.harrypottermagicdictionary.model.Words

@Database(entities = arrayOf(Words::class), version = 1,exportSchema = true)
abstract class WordDatabase : RoomDatabase(){

    abstract fun wordsDao() : wordsDAO

    // Singleton
    companion object{

        @Volatile private var instance : WordDatabase? = null

        private val lock = Any()
        operator  fun invoke(context :Context)= instance?: synchronized(lock){
            instance?: databaseCreate(context).also {
                instance = it
            }
        }

        private fun databaseCreate(context : Context) = Room.databaseBuilder(
            context.applicationContext,
            WordDatabase::class.java,"worddatabase").build()
    }

}