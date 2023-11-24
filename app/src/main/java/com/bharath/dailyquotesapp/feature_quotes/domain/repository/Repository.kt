package com.bharath.dailyquotesapp.feature_quotes.domain.repository

import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto


interface Repository {

    suspend fun getRandomQuote(): QuoteItemDto
    suspend fun getListOfQuotes(pageNo: String): List<QuoteItemDto>

}
