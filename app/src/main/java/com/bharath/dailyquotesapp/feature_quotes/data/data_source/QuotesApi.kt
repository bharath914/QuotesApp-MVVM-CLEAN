package com.bharath.dailyquotesapp.feature_quotes.data.data_source

import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteDto
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface QuotesApi {
    @GET("random")
    suspend fun getRandomQuote(): QuoteItemDto

    @GET("quotes")
    suspend fun getListOfQuotes(@Query("page") page: String):QuoteDto
}

object ApiCons {
    const val BASE_URL = "https://api.quotable.io/"
}