package com.example.vocabbuilder.repositories

import androidx.lifecycle.LiveData
import com.example.vocabbuilder.application.VocabApp
import com.example.vocabbuilder.db.VocabDatabase
import com.example.vocabbuilder.db.WordMeaning

class VocabularyRepositoryImpl: VocabularyRepository {
    private val db: VocabDatabase = VocabApp.VOCAB_DB

    override fun getAllWords(): LiveData<List<WordMeaning>> {
      return  db.vocabularyDao().getAllWords()
    }

     suspend override fun updateWord(
        word: String,
        meaning: String,
        synonym: String,
        example: String
    ) {
         db.vocabularyDao().insertAll(WordMeaning(word,meaning,synonym=synonym,ex1 = example))
    }

    override suspend fun deleteWord(word: String,
                                      meaning: String,
                                      synonym: String,
                                      example: String) {
        db.vocabularyDao().delete(WordMeaning(word,meaning,synonym=synonym,ex1 = example))
    }
}