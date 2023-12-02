package com.bharath.dailyquotesapp.feature_quotes.domain.entity


data class TagItem(
    val _id: String,
    val name: String,
    val quoteCount: Int,
    val slug: String,
)