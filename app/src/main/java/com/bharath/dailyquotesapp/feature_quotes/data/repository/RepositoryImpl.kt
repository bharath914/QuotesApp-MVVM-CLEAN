package com.bharath.dailyquotesapp.feature_quotes.data.repository

import com.bharath.dailyquotesapp.feature_quotes.data.data_source.QuotesApi
import com.bharath.dailyquotesapp.feature_quotes.data.entity.AuthorsDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.search.SearchDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.tags.TagsDtoItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
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

    override suspend fun getListOfAuthors(pageNo: String, sortOrder: String): AuthorsDto {
        return withContext(Dispatchers.IO) {
            api.getAllAuthors(pageNo, sortBy = sortOrder)
        }
    }

    override suspend fun getQuotesByAuthor(pageNo: String, authorName: String): QuoteDto {
        return withContext(Dispatchers.IO) {
            api.getQuotesByAuthor(pageNo, authorName)
        }
    }

    override suspend fun searchQuotes(pageNo: String, query: String): SearchDto {
        return withContext(Dispatchers.IO) {
            api.getQuotesBySearch(query, pageNo)
        }
    }

    override suspend fun searchAuthors(pageNo: String, query: String): AuthorsDto {
        return withContext(IO) {
            api.getAuthorsBySearch(query, pageNo)
        }
    }

    override suspend fun getAllTags(): List<TagsDtoItem> {
        return withContext(IO) {
            api.getAllTags()
        }
    }

    override suspend fun getQuotesByTag(tag:String,pageNo: String): QuoteDto {
        return withContext(IO) {
            api.getQuotesByTag(tag,pageNo)
        }
    }

}