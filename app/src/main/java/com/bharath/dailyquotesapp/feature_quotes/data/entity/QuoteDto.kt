package com.bharath.dailyquotesapp.feature_quotes.data.entity

import androidx.annotation.Keep

@Keep
data class QuoteDto(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Result>,
    val totalCount: Int,
    val totalPages: Int
)