package com.example.harrypottermagicdictionary.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.harrypottermagicdictionary.model.Words
import com.example.harrypottermagicdictionary.servis.WordAPIService
import com.example.harrypottermagicdictionary.servis.WordDatabase
import com.example.harrypottermagicdictionary.util.SpecialSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class WordListViewModel(application: Application) : BaseViewModel(application) {

    val words = MutableLiveData<List<Words>>()
    val wordsError = MutableLiveData<Boolean>()
    val wordsLoading = MutableLiveData<Boolean>()

    private val wordApiService = WordAPIService()
    private val disposable = CompositeDisposable()
    private val speciallSharedPreferences = SpecialSharedPreferences(getApplication())

    private var updateTime = 10*60*1000*1000*1000L

    fun refreshData(){

        val registrationTime = speciallSharedPreferences.timeGet()

        if(registrationTime!=null && registrationTime !=0L && System.nanoTime()-registrationTime<updateTime){
            // Sqlite
            getDataSqlite()
        }else{
            getdataFromInternet()
        }
    }

    fun refreshFromInternet(){
        getdataFromInternet()
    }

    private fun getDataSqlite(){
        wordsLoading.value=true
        launch {
            val spelList = WordDatabase(getApplication()).wordsDao().getAllWord()
            wordsShow(spelList)
            Toast.makeText(getApplication(),"rooom",Toast.LENGTH_LONG).show()
        }
    }

    private fun getdataFromInternet(){
        wordsLoading.value=true

        disposable.add(
            wordApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object:DisposableSingleObserver<List<Words>>(){
                    override fun onSuccess(t: List<Words>) {
                        sqliteSecret(t)
                        Toast.makeText(getApplication(),"interneeet",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        wordsError.value=true
                        wordsLoading.value=false
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun wordsShow(wordList : List<Words>){
        words.value= wordList
        wordsError.value=false
        wordsLoading.value = false
    }

    private fun sqliteSecret(wordList : List<Words>){
        launch {
            val dao  = WordDatabase(getApplication()).wordsDao()
            dao.deleteAllWord()
            val uuidList = dao.insertAll(*wordList.toTypedArray())

            var i = 0
            while(i<wordList.size){
                wordList[i].uuid = uuidList[i].toInt()
                i=i+1
            }
            wordsShow(wordList)
        }
        speciallSharedPreferences.saveTime(System.nanoTime())
    }

}