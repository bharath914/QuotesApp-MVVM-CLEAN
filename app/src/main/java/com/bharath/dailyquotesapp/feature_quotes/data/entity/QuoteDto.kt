package com.bharath.dailyquotesapp.feature_quotes.data.entity

data class QuoteDto(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<Result>,
    val totalCount: Int,
    val totalPages: Int
)