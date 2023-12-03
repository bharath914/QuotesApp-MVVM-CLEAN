package com.bharath.dailyquotesapp.feature_quotes.presentation.saved.events

import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem

sealed class SavedScreenEvents {
    object undoDelete : SavedScreenEvents()
    data class clickOnDelete(val quoteEntity: QuoteItem) : SavedScreenEvents()
}