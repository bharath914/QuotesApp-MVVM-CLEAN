package com.bharath.dailyquotesapp.feature_quotes.data.data_source

import com.bharath.dailyquotesapp.feature_quotes.data.entity.AuthorsDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.search.SearchDto
import retrofit2.http.GET
import retrofit2.http.Query


interface QuotesApi {
    @GET("random")
    suspend fun getRandomQuote(): QuoteItemDto

    @GET("quotes")
    suspend fun getListOfQuotes(@Query("page") page: String): QuoteDto

    @GET("authors")
    suspend fun getAllAuthors(
        @Query("page") page: String,
        @Query("sortBy") sortBy: String,
    ): AuthorsDto

    @GET("quotes")
    suspend fun getQuotesByAuthor(
        @Query("page") page: String,
        @Query("author") author: String,
    ): QuoteDto

    @GET("search/quotes")
    suspend fun getQuotesBySearch(
        @Query("query") query: String,
        @Query("page") page: String,
    ): SearchDto

    @GET("search/authors")
    suspend fun getAuthorsBySearch(
        @Query("query") query: String,
        @Query("page") page: String,

        ):AuthorsDto
}


object ApiCons {
    const val BASE_URL = "https://api.quotable.io/"
}