package com.bharath.dailyquotesapp.feature_quotes.data.entity.search

import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto

data class SearchDto(
    val __info__: Info,
    val count: Int,
    val page: Int,
    val results: List<QuoteItemDto>,
    val totalCount: Int,
    val totalPages: Int
)