package com.example.vocabbuilder

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabbuilder.db.WordMeaning
import com.example.vocabbuilder.viewmodels.VocabListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel

class Dashboard : AppCompatActivity() {

    private val viewmodel: VocabListViewModel by viewModel<VocabListViewModel>()
    val listOFWords:MutableList<WordMeaning> = mutableListOf()
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        recyclerView = findViewById(R.id.recyclerView)
        setupRecyclerView()
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setHomeButtonEnabled(true)
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Log.d(TAG, " fab clicked..")
            Router.navigateTo(this, AddWordActivity::class.java)
        }
    }

    private fun setupRecyclerView() {
        val adapter = VocabDisplayAdapter(listOFWords)
        recyclerView.apply {
            isNestedScrollingEnabled = false
            layoutManager = LinearLayoutManager(this@Dashboard)
        }
        recyclerView.adapter = adapter

        val itemTouch = ItemTouchHelper(
            SwipeToDeleteCallback(
                adapter,
                viewmodel,
                findViewById(R.id.coordinator)
            )
        )
        itemTouch.attachToRecyclerView(recyclerView)
        viewmodel.wordsListLiveData.observe(this, getObserver())
        viewmodel.getAllWords()
    }

    override fun onStop() {
        super.onStop()
        this.viewmodel.deleteWordsFromDb()
    }
    private fun getObserver(): Observer<List<WordMeaning>> {
        return Observer { list ->
            list.map { wordEntry ->
                Log.d(TAG, "${wordEntry.word}, ${wordEntry.meaning}, ${wordEntry.ex1}");

            }
            listOFWords.clear()
            listOFWords.addAll(list)
            recyclerView.adapter!!.notifyDataSetChanged()
        }
    }

    companion object {
        const val TAG = "Dashboard"
    }
}

class SwipeToDeleteCallback(
    val adapter: VocabDisplayAdapter,
    val viewModel: VocabListViewModel,
    val coordinatorLayout: CoordinatorLayout
) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
    val icon = ContextCompat.getDrawable(
        coordinatorLayout.context,
        android.R.drawable.ic_menu_delete
    );
   val background = ColorDrawable(Color.LTGRAY);
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        // left empty since not supported
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val itemPosition = viewHolder.adapterPosition
        val itemToDelete = adapter.wordsList.get(itemPosition)
        when (direction) {
            ItemTouchHelper.RIGHT -> {
                adapter.deleteItem(itemPosition)
                viewModel.addToDeleteList(itemToDelete)
                val view =
                    Snackbar.make(coordinatorLayout, "", Snackbar.LENGTH_LONG)
                        .setAction(R.string.undo) {
                            adapter.restoreItem(viewModel.removeLastDeletedWord(), itemPosition)
                        }
                view.show()
            }
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 10
        val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
        val iconBottom = iconTop + icon!!.intrinsicHeight
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        background.setBounds(
            itemView.getLeft(), itemView.getTop()+10,
            itemView.getLeft() + dX.toInt() + backgroundCornerOffset,
            itemView.getBottom()-10
        )

        val iconLeft: Int = itemView.left + iconMargin + icon!!.intrinsicWidth
        val iconRight: Int = itemView.left + iconMargin
        icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
        background.draw(c)
        icon.draw(c)
    }

}