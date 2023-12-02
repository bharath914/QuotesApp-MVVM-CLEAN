package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.authors.authorDetail

import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem

sealed class AuthorDetailEvent {
    data class OnClickOnTabRow(val index: Int) : AuthorDetailEvent()
    data class onClickFav(val saved:Boolean,val quoteItem: QuoteItem) : AuthorDetailEvent()
}
