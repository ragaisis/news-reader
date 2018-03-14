package com.ragaisis.newsreader.contracts

import android.widget.ImageView
import com.ragaisis.newsreader.BaseView
import com.ragaisis.newsreader.adapters.NewsFeedAdapter
import com.ragaisis.newsreader.entities.NewsResponseArticle
import com.ragaisis.newsreader.presenter.BasePresenter

interface NewsFeedContract {

    interface View : BaseView<Presenter> {

        fun rowItemClicked(item: NewsResponseArticle, sharedImageView: ImageView)

        fun loadNewsFeed(items: List<NewsResponseArticle>)

        fun showError(message: String?)

    }

    interface Presenter : BasePresenter<View> {

        fun loadTasks()

    }
}