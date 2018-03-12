package com.ragaisis.newsreader.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ragaisis.newsreader.R

class NewsFeedViewHolder(view: View, private val clickListener: (item: Int) -> Unit) : RecyclerView.ViewHolder(view) {

    val titleTextView : TextView
    val dateTextView : TextView
    val imageView : ImageView

    init {
        titleTextView = view.findViewById<TextView>(R.id.row_news_title_text_view)
        dateTextView = view.findViewById<TextView>(R.id.row_news_date_text_view)
        imageView = view.findViewById<ImageView>(R.id.row_news_image_view)
        view.setOnClickListener { onClicked() }
    }

    fun onClicked() {
        clickListener.invoke(layoutPosition)
    }
}