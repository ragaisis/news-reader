package com.ragaisis.newsreader.presenter

import android.content.Context
import com.ragaisis.newsreader.api.NewsApi
import com.ragaisis.newsreader.contracts.NewsFeedContract
import com.ragaisis.newsreader.entities.NewsResponseArticle
import com.ragaisis.newsreader.entities.NewsResponseBody
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class NewsFeedPresenter @Inject constructor() : NewsFeedContract.Presenter {

    private var newsFeedView: NewsFeedContract.View? = null

    @Inject
    lateinit var context: Context
    @Inject
    lateinit var api: NewsApi

    override fun loadTasks(forcedRefresh: Boolean) {
        api.getNewsByTopic("Android")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            if (forcedRefresh) {
                                newsFeedView?.stopForceRefresh()
                            }
                            displayList(result)
                        },
                        { error -> newsFeedView?.showError(error.message) })
    }

    override fun forceRefresh() {
        loadTasks(true)
    }

    private fun displayList(response: NewsResponseBody?) {
        val list: List<NewsResponseArticle>? = response?.articles
        if (list != null) {
            newsFeedView?.loadNewsFeed(list)
        }
    }

    override fun takeView(view: NewsFeedContract.View) {
        this.newsFeedView = view;
    }

    override fun dropView() {
        newsFeedView = null;
    }

}