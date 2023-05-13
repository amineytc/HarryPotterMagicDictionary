package com.example.harrypottermagicdictionary.servis

import androidx.room.Dao
import androidx.room.Insert
import com.example.harrypottermagicdictionary.model.Words

@Dao
interface wordsDAO {

    @Insert
    suspend fun insertAll(vararg word: Words): List<Long>

    @androidx.room.Query("SELECT * FROM word")
    suspend fun getAllWord() : List<Words>

    @androidx.room.Query("SELECT * FROM word WHERE uuid= :wordId")
    suspend fun getWord(wordId :Int) : Words

    @androidx.room.Query("DELETE FROM word")
    suspend fun deleteAllWord()
}