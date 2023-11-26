package com.bharath.dailyquotesapp.feature_quotes.presentation.saved.events

import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity

sealed class SavedScreenEvents {
    object undoDelete : SavedScreenEvents()
    data class clickOnDelete(val savedQuoteEntity: SavedQuoteEntity) : SavedScreenEvents()
}