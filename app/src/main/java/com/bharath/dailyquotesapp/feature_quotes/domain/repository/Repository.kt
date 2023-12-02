package com.bharath.dailyquotesapp.feature_quotes.domain.repository

import com.bharath.dailyquotesapp.feature_quotes.data.entity.AuthorsDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.search.SearchDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.tags.TagsDtoItem


interface Repository {

    suspend fun getRandomQuote(): QuoteItemDto
    suspend fun getListOfQuotes(pageNo: String): QuoteDto

    suspend fun getListOfAuthors(pageNo: String, sortOrder: String): AuthorsDto

    suspend fun getQuotesByAuthor(pageNo: String, authorName: String): QuoteDto

    suspend fun searchQuotes(pageNo: String, query: String): SearchDto

    suspend fun searchAuthors(pageNo: String, query: String): AuthorsDto

    suspend fun getAllTags(): List<TagsDtoItem>

    suspend fun getQuotesByTag(tag:String):QuoteDto



}




