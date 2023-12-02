package com.bharath.dailyquotesapp.feature_quotes.data.entity.search

data class Result(
    val _id: String,
    val author: String,
    val authorId: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>
)