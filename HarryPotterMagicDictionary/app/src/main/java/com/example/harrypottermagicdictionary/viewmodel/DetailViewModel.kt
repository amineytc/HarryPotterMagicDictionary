package com.example.harrypottermagicdictionary.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.harrypottermagicdictionary.model.Words
import com.example.harrypottermagicdictionary.servis.WordDatabase
import kotlinx.coroutines.launch

class DetailViewModel(application : Application) : BaseViewModel(application) {

    val wordLiveData = MutableLiveData<Words>()

    fun roomDataGet(uuid :Int){

        launch {

            val dao = WordDatabase(getApplication()).wordsDao()
            val word = dao.getWord(uuid)
            wordLiveData.value=word
        }
    }


}