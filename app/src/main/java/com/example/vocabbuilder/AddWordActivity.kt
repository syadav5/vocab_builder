package com.example.vocabbuilder

import android.os.Bundle
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.vocabbuilder.databinding.ActivityAddWordBinding
import com.example.vocabbuilder.db.WordMeaning
import com.example.vocabbuilder.viewmodels.AddWordViewModel
import org.koin.androidx.viewmodel.compat.ScopeCompat.viewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddWordActivity : AppCompatActivity() {
    private var binding: ActivityAddWordBinding? = null
    val viewModel: AddWordViewModel by viewModel<AddWordViewModel>()
    private var word: WordMeaning? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.confirmationLiveData.observe(this, getObserver())
        binding!!.saveBtn.setOnClickListener { view -> saveWord() }
        word = intent.extras?.getParcelable<WordMeaning>(RouteParam.NOTE_TO_EDIT)
        word?.let {
            setData(it)
        }
    }

    private fun getObserver(): Observer<Boolean> = object : Observer<Boolean> {
        override fun onChanged(t: Boolean?) {
            Toast.makeText(this@AddWordActivity, "Data saved", Toast.LENGTH_SHORT).show()
            clearData()
        }

    }

    private fun saveWord() {
        binding?.let {
            if (!validateInput()) {
                Toast.makeText(this, "Please enter all the required Fields", Toast.LENGTH_SHORT)
                    .show()
            }
            (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                window.currentFocus?.windowToken,
                0
            )
            viewModel.addOrUpdateWord(
                word = it.wordEt.editableText?.toString() ?: "",
                meaning = it.meaningEt.editableText?.toString() ?: "",
                synonym = it.synonymEt.editableText?.toString() ?: "",
                example = it.exampleUsage.editableText?.toString() ?: ""
            )
        }
    }

    fun clearData() {
        binding?.apply {
            wordEt.editableText.clear()
            meaningEt.editableText.clear()
            exampleUsage.editableText.clear()
            synonymEt.editableText.clear()
        }
    }

    fun setData(word: WordMeaning) {
        binding?.apply {
            wordEt.setText(word.word)
            meaningEt.setText(word.meaning)
            synonymEt.setText(word.synonym)
            exampleUsage.setText(word.ex1)
        }
    }

    private fun validateInput() = notEmpty(binding!!.wordEt.editableText?.toString()) &&
            notEmpty(binding!!.meaningEt.editableText?.toString()) &&
            notEmpty(binding!!.meaningEt.editableText?.toString())

    fun notEmpty(value: String?) = !TextUtils.isEmpty(value)

}
