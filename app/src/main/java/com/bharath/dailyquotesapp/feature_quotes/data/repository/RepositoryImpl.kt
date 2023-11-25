package com.bharath.dailyquotesapp.feature_quotes.data.repository

import com.bharath.dailyquotesapp.feature_quotes.data.data_source.QuotesApi
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: QuotesApi,
) : Repository {
    override suspend fun getRandomQuote(): QuoteItemDto {
        return withContext(Dispatchers.IO) {
            api.getRandomQuote()
        }
    }

    override suspend fun getListOfQuotes(pageNo: String): QuoteDto {

        return withContext(Dispatchers.IO) {
            api.getListOfQuotes(pageNo)
        }
    }

}