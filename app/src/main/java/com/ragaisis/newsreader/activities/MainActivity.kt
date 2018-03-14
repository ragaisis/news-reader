package com.ragaisis.newsreader.activities

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.view.ViewCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ImageView
import android.widget.Toast
import com.google.gson.Gson
import com.ragaisis.newsreader.MainApplication
import com.ragaisis.newsreader.R
import com.ragaisis.newsreader.adapters.NewsFeedAdapter
import com.ragaisis.newsreader.contracts.NewsFeedContract
import com.ragaisis.newsreader.entities.NewsResponseArticle
import com.ragaisis.newsreader.presenter.NewsFeedPresenter
import javax.inject.Inject

class MainActivity : AppCompatActivity(), NewsFeedContract.View {

    @Inject
    lateinit var presenter: NewsFeedPresenter
    @Inject
    lateinit var gson: Gson

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var adapter: NewsFeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (application as MainApplication).component.inject(this)
        initViews()
    }

    private fun initViews() {
        recyclerView = findViewById<RecyclerView>(R.id.activity_main_swipe_refresh_layout_recycler_view)
        adapter = NewsFeedAdapter(this, ArrayList(0))
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.activity_main_swipe_refresh_layout)
        swipeRefreshLayout.isRefreshing = false
        swipeRefreshLayout.setOnRefreshListener({
            presenter.forceRefresh()
        })
    }

    override fun rowItemClicked(item: NewsResponseArticle, sharedImageView: ImageView) {
        val newIntent = Intent(this, DetailsActivity::class.java)
        DetailsActivity.setMessage(newIntent, gson.toJson(item))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    sharedImageView,
                    ViewCompat.getTransitionName(sharedImageView))
            startActivity(newIntent, options.toBundle())
        } else {
            startActivity(newIntent)
        }
    }

    override fun stopForceRefresh() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun loadNewsFeed(items: List<NewsResponseArticle>) {
        adapter.items = items
        adapter.onClickListener = { article: NewsResponseArticle, imageView: ImageView ->
            rowItemClicked(article, imageView)
        }
    }

    override fun showError(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.takeView(this)
        presenter.loadTasks(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dropView();
    }
}
