package com.ragaisis.newsreader

import com.ragaisis.newsreader.contracts.NewsFeedDetailsContract
import com.ragaisis.newsreader.entities.NewsResponseArticle
import com.ragaisis.newsreader.presenter.NewsFeedDetailsPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.Mockito.`when` as whenever

class NewsFeedDetailsUnitTest {

    @Mock
    private val newsFeedDetailsView: NewsFeedDetailsContract.View? = null

    private lateinit var detailsPresenter: NewsFeedDetailsPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        detailsPresenter = NewsFeedDetailsPresenter()
        if (newsFeedDetailsView != null) {
            detailsPresenter.takeView(newsFeedDetailsView)
        }
    }

    @Test
    fun open_article_url_in_web_browser_test() {
        val url = "http://15min.lt"
        val article = NewsResponseArticle(null, "", "", "", url, "", "")
        detailsPresenter.init(article)
        detailsPresenter.readFullArticleButtonClicked()
        verify(newsFeedDetailsView, times(1))?.openWebBrowser(url)
    }

    @Test
    fun article_url_is_not_changed_when_opened_in_web_browser_test() {
        val url = "http://15min.lt"
        val article = NewsResponseArticle(null, "", "", "", url, "", "")
        detailsPresenter.init(article)
        detailsPresenter.readFullArticleButtonClicked()
        verify(newsFeedDetailsView, times(1))?.openWebBrowser(article.url)
    }

    @Test
    fun article_url_is_not_image_url_when_opened_in_web_browser_test() {
        val url = "http://15min.lt"
        val imgUrl = "http://15min.lt/2.png"
        val article = NewsResponseArticle(null, "", "", "", url, imgUrl, "")
        detailsPresenter.init(article)
        detailsPresenter.readFullArticleButtonClicked()
        verify(newsFeedDetailsView, times(0))?.openWebBrowser(article.urlToImage)
    }

    @Test
    fun show_error_with_nullable_article() {
        detailsPresenter.init(null)
        detailsPresenter.loadUi()
        verify(newsFeedDetailsView, times(0))?.updateUI(null)
        verify(newsFeedDetailsView, times(1))?.showError()
    }

}
