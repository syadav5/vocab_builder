package com.example.vocabbuilder

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class ListTile(
    context: Context,
    attributeSet: AttributeSet,
    defStyleAttr: Int,
    defStyleRes: Int
) : LinearLayout(context, attributeSet, defStyleAttr, defStyleRes) {

    private var wordTv: TextView? = null
    private var meaningTv: TextView? = null
    private var exampleTv: TextView? = null

    private var synonym: TextView? = null
    private var img: ImageView? = null

    constructor(context: Context, attributeSet: AttributeSet) : this(context, attributeSet, 0, 0) {
        init()
    }

    fun init() {
        val root = LayoutInflater.from(context).inflate(R.layout.list_tile, this, true)
        root?.apply {
            orientation = HORIZONTAL
            wordTv = findViewById(R.id.wordTv)
            meaningTv = findViewById(R.id.meaning)
            exampleTv = findViewById(R.id.exmpl1)
            synonym = findViewById(R.id.synonym)
            img = findViewById(R.id.imageView)
            img!!.visibility = GONE
            exampleTv!!.visibility = GONE
        }
    }

    fun setData(
        word: String,
        meaning: String,
        exampl: String? = null,
        synonym: String? = null,
        imgSrc: Int = View.NO_ID
    ) {
        wordTv!!.text = word
        meaningTv!!.text = meaning
        exampl?.let {
            exampleTv!!.text = "e.g. ${exampl}"
            exampleTv!!.visibility = VISIBLE
        }
        synonym?.let {
            this.synonym!!.text = "synonyms: $synonym"
            this.synonym!!.visibility = VISIBLE
        }
        if (imgSrc != View.NO_ID) {
            img!!.setImageResource(imgSrc)
            img!!.visibility = View.VISIBLE
        }
    }
}