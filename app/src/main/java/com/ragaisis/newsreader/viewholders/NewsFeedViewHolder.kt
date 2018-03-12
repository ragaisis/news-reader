package com.ragaisis.newsreader.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.ragaisis.newsreader.R

class NewsFeedViewHolder(view: View, private val clickListener: (item: Int) -> Unit) : RecyclerView.ViewHolder(view) {

    val titleTextView : TextView

    init {
        titleTextView = view.findViewById<TextView>(R.id.row_news_item_text_view)
        view.setOnClickListener { onClicked() }
    }

    fun onClicked() {
        clickListener.invoke(layoutPosition)
    }
}