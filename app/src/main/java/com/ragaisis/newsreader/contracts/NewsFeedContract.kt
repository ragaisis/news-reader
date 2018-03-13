package com.ragaisis.newsreader.contracts

import android.widget.ImageView
import com.ragaisis.newsreader.BaseView
import com.ragaisis.newsreader.adapters.NewsFeedAdapter
import com.ragaisis.newsreader.entities.NewsResponseArticle
import com.ragaisis.newsreader.presenter.BasePresenter

interface NewsFeedContract {

    interface View : BaseView<Presenter> {

        fun itemClicked(item: NewsResponseArticle, sharedImageView: ImageView)

    }

    interface Presenter : BasePresenter<View> {

        fun createNewsFeedAdapter() : NewsFeedAdapter

        fun loadTasks()

    }
}