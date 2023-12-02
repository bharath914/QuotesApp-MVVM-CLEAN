package com.bharath.dailyquotesapp.feature_quotes.presentation.search

sealed class SearchEvent {

    object clickedOnEnter : SearchEvent()
    data class onSearchText(val string: String) : SearchEvent()

}