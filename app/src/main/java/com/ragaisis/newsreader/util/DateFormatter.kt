package com.ragaisis.newsreader.util

import java.text.SimpleDateFormat
import java.util.*

fun getDisplayableDate(date: String?): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val resultSdf = SimpleDateFormat("yyyy-MMMM-dd HH:mm", Locale.getDefault())
    return resultSdf.format(sdf.parse(date))
}