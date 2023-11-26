package com.bharath.dailyquotesapp.feature_quotes.data.repository

import com.bharath.dailyquotesapp.feature_quotes.data.data_source.QuotesApi
import com.bharath.dailyquotesapp.feature_quotes.data.entity.AuthorsDto
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

    override suspend fun getListOfAuthors(pageNo: String, sortOrder: String):AuthorsDto {
        return withContext(Dispatchers.IO) {
            api.getAllAuthors(pageNo, sortBy = sortOrder)
        }
    }

    override suspend fun getQuotesByAuthor(pageNo: String, authorName: String): QuoteDto {
        return withContext(Dispatchers.IO){
            api.getQuotesByAuthor(pageNo,authorName)
        }
    }

}