package com.ragaisis.newsreader.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.content.res.AppCompatResources
import android.support.v7.widget.Toolbar
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.ragaisis.newsreader.MainApplication
import com.ragaisis.newsreader.R
import com.ragaisis.newsreader.contracts.NewsFeedDetailsContract
import com.ragaisis.newsreader.entities.NewsResponseArticle
import com.ragaisis.newsreader.presenter.NewsFeedDetailsPresenter
import com.ragaisis.newsreader.util.getDisplayableDate
import javax.inject.Inject

class DetailsActivity : AppCompatActivity(), NewsFeedDetailsContract.View {

    companion object {
        private const val ARTICLE_ITEM = "json_item"

        fun getMessage(intent: Intent): String? {
            return intent.getStringExtra(ARTICLE_ITEM)
        }

        fun setMessage(intent: Intent, message: String?) {
            intent.putExtra(ARTICLE_ITEM, message)
        }
    }

    @Inject
    lateinit var gson: Gson
    @Inject
    lateinit var presenter: NewsFeedDetailsPresenter

    lateinit var titleTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var authorTextView: TextView
    lateinit var dateTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        (application as MainApplication).component.inject(this)
        initViews()
        presenter.init(gson.fromJson(DetailsActivity.getMessage(intent), NewsResponseArticle::class.java))
    }

    private fun initViews() {
        titleTextView = findViewById<TextView>(R.id.activity_details_title_text_view)
        descriptionTextView = findViewById<TextView>(R.id.activity_details_description_text_view)
        authorTextView = findViewById<TextView>(R.id.activity_details_artist_text_view)
        dateTextView = findViewById<TextView>(R.id.activity_details_date_text_view)
        findViewById<Button>(R.id.activity_details_button).setOnClickListener {
            presenter.readFullArticleButtonClicked()
        }
        initCollapsingToolbar()
    }

    private fun initCollapsingToolbar() {
        val appBarLayout = findViewById<AppBarLayout>(R.id.app_bar_layout)
        val collapsingToolbarLayout: CollapsingToolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar)
        collapsingToolbarLayout.title = resources.getString(R.string.app_name)
        setSupportActionBar(findViewById<Toolbar>(R.id.toolbar))
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
        getSupportActionBar()?.setDisplayShowHomeEnabled(true)
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = true
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.title = resources.getString(R.string.app_name)
                    isShow = true
                } else if (isShow) {
                    collapsingToolbarLayout.title = " "
                    isShow = false
                }
            }
        })
    }

    override fun updateUI(article: NewsResponseArticle?) {
        titleTextView.setText(article?.title)
        descriptionTextView.setText(article?.description)
        authorTextView.setText(article?.author)
        dateTextView.setText(getDisplayableDate(article?.publishedAt))
        Glide.with(this)
                .load(article?.urlToImage)
                .into(findViewById<ImageView>(R.id.activity_details_image_view))
                .onLoadFailed(AppCompatResources.getDrawable(this, R.mipmap.ic_launcher))
    }

    override fun openWebBrowser(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
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