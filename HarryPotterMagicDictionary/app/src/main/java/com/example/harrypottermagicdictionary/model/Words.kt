package com.example.harrypottermagicdictionary.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity("word")
data class Words (
    @ColumnInfo(name="spell")
    @SerializedName("spell")
    val wordName:String?,
    @ColumnInfo(name="explanation")
    @SerializedName("explanation")
    val wordDetail :String?,
    @ColumnInfo(name="image")
    @SerializedName("image")
    val wordPicture:String?){


    @PrimaryKey(autoGenerate = true)
    var uuid :Int = 0
}