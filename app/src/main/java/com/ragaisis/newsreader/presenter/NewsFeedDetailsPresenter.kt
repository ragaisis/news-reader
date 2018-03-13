package com.ragaisis.newsreader.presenter

import android.content.Context
import com.ragaisis.newsreader.contracts.NewsFeedDetailsContract
import com.ragaisis.newsreader.entities.NewsResponseArticle
import javax.inject.Inject

class NewsFeedDetailsPresenter @Inject constructor() : NewsFeedDetailsContract.Presenter {

    private var newsFeedDetailsView: NewsFeedDetailsContract.View? = null
    private var article: NewsResponseArticle? = null

    @Inject
    lateinit var context: Context

    fun init(article: NewsResponseArticle?) {
        this.article = article
        newsFeedDetailsView?.updateUI(article)
    }

    override fun readFullArticleButtonClicked() {
        newsFeedDetailsView?.openWebBrowser(article?.url)
    }

    override fun takeView(view: NewsFeedDetailsContract.View) {
        newsFeedDetailsView = view
    }

    override fun dropView() {
        newsFeedDetailsView = null;
    }

}