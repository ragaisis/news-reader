package com.ragaisis.newsreader.activities

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.ragaisis.newsreader.MainApplication
import com.ragaisis.newsreader.R
import com.ragaisis.newsreader.contracts.NewsFeedContract
import com.ragaisis.newsreader.presenter.NewsFeedPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NewsFeedContract.View {

    @Inject
    lateinit var presenter: NewsFeedPresenter

    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MainApplication).component.inject(this)
        initViews()
        presenter.loadTasks()
    }

    private fun initViews() {
        recyclerView = findViewById<RecyclerView>(R.id.activity_main_swipe_refresh_layout_recycler_view)
        recyclerView.adapter = presenter.createNewsFeedAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)

        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.activity_main_swipe_refresh_layout)
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.setOnRefreshListener({
            presenter.loadTasks()
            swipeRefreshLayout.isRefreshing = false
        })
    }

    override fun itemClicked(item: String) {
        Toast.makeText(this, item, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this);
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView();
    }
}
