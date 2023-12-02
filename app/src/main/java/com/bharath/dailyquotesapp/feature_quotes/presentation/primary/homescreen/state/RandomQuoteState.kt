package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.homescreen.state

import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem

data class RandomQuoteState(
    val quoteItem: QuoteItem = QuoteItem(),
    val isLoading: Boolean = false,
    val error: String = "",
)
