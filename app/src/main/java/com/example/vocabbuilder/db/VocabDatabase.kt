package com.example.vocabbuilder.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(WordMeaning::class), version = 2)
abstract class VocabDatabase:RoomDatabase(){
    abstract fun vocabularyDao(): VocabDao
}