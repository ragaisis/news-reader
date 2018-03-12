package com.ragaisis.newsreader.presenter

import android.content.Context
import com.ragaisis.newsreader.adapters.NewsFeedAdapter
import com.ragaisis.newsreader.contracts.NewsFeedContract
import javax.inject.Inject

class NewsFeedPresenter @Inject constructor() : NewsFeedContract.Presenter {

    private var newsFeedView: NewsFeedContract.View? = null

    @Inject
    lateinit var context: Context

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
        adapter.items = arrayListOf("1", "2", "3")
        adapter.notifyDataSetChanged()
    }

    override fun takeView(view: NewsFeedContract.View) {
        this.newsFeedView = view;
    }

    override fun dropView() {
        newsFeedView = null;
    }

}