package com.example.vocabbuilder.repositories

import androidx.lifecycle.LiveData
import com.example.vocabbuilder.db.WordMeaning

interface VocabularyRepository {
    fun getAllWords(): LiveData<List<WordMeaning>>
    suspend fun updateWord(word: String, meaning: String, synonym: String, example: String)
    suspend fun deleteWord(word: WordMeaning)
    suspend fun deletedWords(words: List<WordMeaning>)
}