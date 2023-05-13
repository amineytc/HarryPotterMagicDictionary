package com.example.harrypottermagicdictionary.util

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SpecialSharedPreferences {

    companion object{

        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile private  var instance : SpecialSharedPreferences? = null
        private val lock = Any()
        operator  fun invoke(context:Context) : SpecialSharedPreferences = instance ?: synchronized(lock){
            instance ?: specialSharedPreferencesMake(context).also {
                instance =it

            }
        }

        private fun specialSharedPreferencesMake(context : Context) : SpecialSharedPreferences{
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpecialSharedPreferences()
        }
    }

    fun saveTime(time : Long){
        sharedPreferences?.edit(commit = true){
            putLong(TIME,time)
        }
    }

    fun timeGet() = sharedPreferences?.getLong(TIME,0)


}