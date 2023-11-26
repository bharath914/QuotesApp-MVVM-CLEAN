package com.bharath.dailyquotesapp.feature_quotes.data.entity

import androidx.annotation.Keep

@Keep
data class AuthorsDto(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<ResultAuthor>,
    val totalCount: Int,
    val totalPages: Int
)