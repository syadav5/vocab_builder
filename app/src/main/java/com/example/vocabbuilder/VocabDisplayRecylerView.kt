package com.example.vocabbuilder

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.vocabbuilder.db.WordMeaning

class VocabDisplayAdapter(val wordsList: List<WordMeaning>) :
    RecyclerView.Adapter<VocabDisplayViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VocabDisplayViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.each_word_row, parent, false)
        return VocabDisplayViewHolder(view)
    }

    override fun onBindViewHolder(holder: VocabDisplayViewHolder, position: Int) {
        this.wordsList.get(position)?.apply {
            holder.listTile.setData(this.word, this.meaning, synonym = this.synonym, exampl = this.ex1)
            holder.itemView.setOnClickListener { v ->
                Router.apply {
                    addQueryParam(RouteParam.NOTE_TO_EDIT, wordsList.get(position))
                    navigateTo(v.context, AddWordActivity::class.java)
                }
            }
        }
    }

    override fun getItemCount(): Int =
        this.wordsList.size

}

class VocabDisplayViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val listTile: ListTile = itemView.findViewById(R.id.listtile)
}