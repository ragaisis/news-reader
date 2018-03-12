package com.ragaisis.newsreader.entities

class NewsResponseArticle(
        val source: NewsResponseArticleSource?,
        val author: String?,
        val title: String?,
        val description: String?,
        val url: String?,
        val urlToImage: String?,
        val publishedAt: String?
)