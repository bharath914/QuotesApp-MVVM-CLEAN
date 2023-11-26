package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.events

import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem

sealed class HomeEvents {
    object ShowNavigationDrawer : HomeEvents()
    data class ClickedOnFavButton(val quoteItem: QuoteItem,val saved:Boolean) : HomeEvents()
}