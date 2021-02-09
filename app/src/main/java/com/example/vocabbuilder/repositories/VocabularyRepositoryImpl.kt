package com.example.vocabbuilder.repositories

import androidx.lifecycle.LiveData
import com.example.vocabbuilder.application.VocabApp
import com.example.vocabbuilder.db.VocabDatabase
import com.example.vocabbuilder.db.WordMeaning
import org.koin.java.KoinJavaComponent.inject

class VocabularyRepositoryImpl: VocabularyRepository {
     val db: VocabDatabase by inject(VocabDatabase::class.java)

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

    override suspend fun deleteWord(word: WordMeaning) {
        db.vocabularyDao().delete(word)

    }
    override suspend fun deletedWords(words: List<WordMeaning>) {
        db.vocabularyDao().delete(words)
    }
}