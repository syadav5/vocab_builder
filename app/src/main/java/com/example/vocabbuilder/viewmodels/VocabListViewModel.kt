package com.example.vocabbuilder.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.example.vocabbuilder.db.WordMeaning
import com.example.vocabbuilder.repositories.VocabularyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class VocabListViewModel(application: Application, val repository: VocabularyRepository) :
    AndroidViewModel(application) {

    val ioScopeContext = CoroutineScope(Dispatchers.IO)
    val listOfDeletedWords = ArrayDeque<WordMeaning>()

    private val _wordsListLiveData: MediatorLiveData<List<WordMeaning>> = MediatorLiveData()
    val wordsListLiveData: LiveData<List<WordMeaning>> = _wordsListLiveData

    fun getAllWords() {
        ioScopeContext.launch {
            val livedata = repository.getAllWords()
            _wordsListLiveData.addSource(livedata, Observer {
                _wordsListLiveData.postValue(it)
                println("Received data from database.. ${it}")
            })
        }
    }
    fun addToDeleteList(word: WordMeaning) {
        listOfDeletedWords.addLast(word);
        listOfDeletedWords.map { item -> println("The list items after addition are : ${item.word}") }
    }

    fun removeLastDeletedWord(): WordMeaning {
        val lastElement = listOfDeletedWords.removeLast()
        println("The last item deleted was : ${lastElement.word}")
        return lastElement
    }

    fun deleteWordsFromDb() {
        if(listOfDeletedWords.isEmpty()){
            return
        }
        ioScopeContext.launch {
            repository.deletedWords(listOfDeletedWords.toList());
        }
    }
}