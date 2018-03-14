package com.ragaisis.newsreader.contracts

import com.ragaisis.newsreader.BaseView
import com.ragaisis.newsreader.entities.NewsResponseArticle
import com.ragaisis.newsreader.presenter.BasePresenter

interface NewsFeedDetailsContract {

    interface View : BaseView<Presenter> {
        fun showError()

        fun updateUI(article: NewsResponseArticle?)

        fun openWebBrowser(url: String?)
    }

    interface Presenter : BasePresenter<View> {

        fun readFullArticleButtonClicked()

    }
}