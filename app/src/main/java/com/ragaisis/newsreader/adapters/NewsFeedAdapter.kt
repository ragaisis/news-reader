package com.ragaisis.newsreader.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ragaisis.newsreader.R
import com.ragaisis.newsreader.viewholders.NewsFeedViewHolder

class NewsFeedAdapter(context: Context, items: List<String>) : RecyclerView.Adapter<NewsFeedViewHolder>() {

    private val inflater: LayoutInflater

    var onClickListener: ((value: String) -> Unit)? = null
    var items: List<String> = items
        set(items) {
            field = items
            notifyDataSetChanged()
        }

    init {
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsFeedViewHolder {
        return NewsFeedViewHolder(inflater.inflate(R.layout.row_news_item, parent, false), clickListener = {
            onClickListener?.invoke(items[it])
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NewsFeedViewHolder, position: Int) {
        val item = items[position]
        holder.titleTextView.text = item
        holder.dateTextView.text = item
    }

}