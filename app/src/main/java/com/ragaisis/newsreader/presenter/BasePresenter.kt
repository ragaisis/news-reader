package com.ragaisis.newsreader.presenter

interface BasePresenter<T> {

    fun takeView(view: T)

    fun dropView()
}