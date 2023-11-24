package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.state

import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem

data class QuotesListState(
    val quotesList: List<QuoteItem> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
)