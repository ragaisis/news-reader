package com.ragaisis.newsreader.presenter

import android.content.Context
import android.widget.Toast
import com.ragaisis.newsreader.adapters.NewsFeedAdapter
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

    private lateinit var adapter: NewsFeedAdapter

    override fun createNewsFeedAdapter(): NewsFeedAdapter {
        adapter = NewsFeedAdapter(context, ArrayList(0))
        adapter.onClickListener = { item -> onRowClicked(item) }
        return adapter
    }

    private fun onRowClicked(item: String) {
        newsFeedView?.itemClicked(item)
    }

    override fun loadTasks() {
        api.getNewsByTopic("Android")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> displayList(result) },
                        { error -> showError(error) })
    }

    private fun displayList(response: NewsResponseBody?) {
        val list: List<NewsResponseArticle>? = response?.articles
        if (list != null) {
            adapter.items = list
        }
    }

    private fun showError(error: Throwable?) {
        Toast.makeText(context, error?.message, Toast.LENGTH_SHORT).show()
    }

    override fun takeView(view: NewsFeedContract.View) {
        this.newsFeedView = view;
    }

    override fun dropView() {
        newsFeedView = null;
    }

}