package com.example.vocabbuilder

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabbuilder.db.WordMeaning
import com.example.vocabbuilder.viewmodels.AddWordViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Dashboard : AppCompatActivity() {

    /*val db: VocabDatabase by lazy {
        VocabApp.VOCAB_DB
    }*/
    lateinit var viewmodel: AddWordViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        recyclerView = findViewById(R.id.recyclerView)
        viewmodel = ViewModelProviders.of(this).get(AddWordViewModel::class.java)
        setupRecyclerView()
        // findViewById<ListTile>(R.id.listtile).setData("Benign","Kind or good", "He is a benign soul")
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setHomeButtonEnabled(true)


        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Log.d(TAG, " fab clicked..")
            Router.navigateTo(this, AddWordActivity::class.java)
        }
    }

    private fun setupRecyclerView() {
        recyclerView.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@Dashboard)
        }
        viewmodel.wordsListLiveData.observe(this, getObserver())
        viewmodel.getAllWords()

        /*CoroutineScope(Dispatchers.IO).launch {
            val list = db.vocabularyDao().getAllWords()
         *//*   list.map { wordEntry ->
                Log.d(TAG, "${wordEntry.word}, ${wordEntry.meaning}, ${wordEntry.ex1}");

            }*//*
            withContext(Dispatchers.Main) {
                list.observe(this@Dashboard, getObserver())
            }
        }*/

    }

    private fun getObserver(): Observer<List<WordMeaning>> {
        return Observer { list ->
            list.map { wordEntry ->
                Log.d(TAG, "${wordEntry.word}, ${wordEntry.meaning}, ${wordEntry.ex1}");

            }
            recyclerView.adapter = VocabDisplayAdapter(list)
            recyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    companion object {
        const val TAG = "Dashboard"
    }
}
