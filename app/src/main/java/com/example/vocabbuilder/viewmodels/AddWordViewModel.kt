package com.example.vocabbuilder.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.vocabbuilder.db.WordMeaning
import com.example.vocabbuilder.repositories.VocabularyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class AddWordViewModel(application: Application) : AndroidViewModel(application) {
    /*  val repository: VocabularyRepository by lazy { VocabularyRepositoryImpl() }
  */
    val repository: VocabularyRepository by inject(VocabularyRepository::class.java)

    val ioScopeContext = CoroutineScope(Dispatchers.IO)

    private val _wordsListLiveData: MediatorLiveData<List<WordMeaning>> = MediatorLiveData()
    val wordsListLiveData: LiveData<List<WordMeaning>> = _wordsListLiveData

    private val _confirmationLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val confirmationLiveData: LiveData<Boolean> = _confirmationLiveData

    fun getAllWords() {
        ioScopeContext.launch {
            val livedata = repository.getAllWords()
            _wordsListLiveData.addSource(livedata, Observer {
                _wordsListLiveData.postValue(it)
                println("Received data from database.. ${it}")
            })
        }
    }

    fun addOrUpdateWord(word: String, meaning: String, synonym: String, example: String) {
        ioScopeContext.launch {
            repository.updateWord(word, meaning, synonym, example)
            _confirmationLiveData.postValue(true)

        }
    }
}