package com.example.harrypottermagicdictionary.servis

import com.example.harrypottermagicdictionary.model.Words
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WordAPIService {

    // https://raw.githubusercontent.com/amineytc/HPMagicDictionary_JSONDATASET/main/spells.json

    // BASE_URL -> https://raw.githubusercontent.com/
    // amineytc/HPMagicDictionary_JSONDATASET/main/spells.json

    private val BASE_URL = "https://raw.githubusercontent.com/"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(wordAPI::class.java)

    fun getData() : Single<List<Words>> {
        return api.getWord()
    }




}