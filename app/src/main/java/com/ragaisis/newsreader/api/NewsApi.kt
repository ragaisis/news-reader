package com.ragaisis.newsreader.api

import com.ragaisis.newsreader.entities.NewsResponseBody
import io.reactivex.Observable
import retrofit2.adapter.rxjava.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("everything")
    fun getNewsByTopic(@Query("q") topic : String) : Observable<Result<NewsResponseBody>>

}