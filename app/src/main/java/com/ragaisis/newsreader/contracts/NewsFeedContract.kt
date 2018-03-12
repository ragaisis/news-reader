package com.ragaisis.newsreader.contracts

import com.ragaisis.newsreader.BaseView
import com.ragaisis.newsreader.adapters.NewsFeedAdapter
import com.ragaisis.newsreader.presenter.BasePresenter

interface NewsFeedContract {

    interface View : BaseView<Presenter> {

        fun itemClicked(item: String)

    }

    interface Presenter : BasePresenter<View> {

        fun createNewsFeedAdapter() : NewsFeedAdapter

        fun loadTasks()

    }
}