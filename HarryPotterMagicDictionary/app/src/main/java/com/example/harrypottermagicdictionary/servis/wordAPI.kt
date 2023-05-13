package com.example.harrypottermagicdictionary.servis

import com.example.harrypottermagicdictionary.model.Words
import io.reactivex.Single
import retrofit2.http.GET

interface wordAPI {

    // https://raw.githubusercontent.com/amineytc/HPMagicDictionary_JSONDATASET/main/spells.json

    // BASE_URL -> https://raw.githubusercontent.com/
    // amineytc/HPMagicDictionary_JSONDATASET/main/spells.json

    @GET("amineytc/HPMagicDictionary_JSONDATASET/main/spells.json")
    fun getWord() : Single<List<Words>>
}