package com.bharath.dailyquotesapp.feature_quotes.data.entity

import androidx.annotation.Keep

@Keep
data class ResultAuthor(
    val _id: String,
    val bio: String,
    val dateAdded: String,
    val dateModified: String,
    val description: String,
    val link: String,
    val name: String,
    val quoteCount: Int,
    val slug: String
)