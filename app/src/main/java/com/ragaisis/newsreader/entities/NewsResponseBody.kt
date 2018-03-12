package com.ragaisis.newsreader.entities

class NewsResponseBody(
        val status: String?,
        val totalResults: Long?,
        val articles : List<NewsResponseArticle>?
)