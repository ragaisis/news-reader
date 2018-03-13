package com.ragaisis.newsreader.adapters

import android.content.Context
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ragaisis.newsreader.R
import com.ragaisis.newsreader.entities.NewsResponseArticle
import com.ragaisis.newsreader.util.getDisplayableDate
import com.ragaisis.newsreader.viewholders.NewsFeedViewHolder

class NewsFeedAdapter(val context: Context, items: List<NewsResponseArticle>) : RecyclerView.Adapter<NewsFeedViewHolder>() {

    private val inflater: LayoutInflater

    var onClickListener: ((value: NewsResponseArticle) -> Unit)? = null
    var items: List<NewsResponseArticle> = items
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
        holder.titleTextView.text = items[position].title
        Glide.with(context)
                .load(items[position].urlToImage)
                .into(holder.imageView)
                .onLoadFailed(AppCompatResources.getDrawable(context, R.mipmap.ic_launcher))
        holder.dateTextView.text = getDisplayableDate(items[position].publishedAt)
    }

}