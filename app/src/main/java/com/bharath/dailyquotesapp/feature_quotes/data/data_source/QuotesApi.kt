package com.bharath.dailyquotesapp.feature_quotes.data.data_source

import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import retrofit2.http.GET
import retrofit2.http.Path


interface QuotesApi {
    @GET("random")
    suspend fun getRandomQuote(): QuoteItemDto

    @GET("quotes?page={pageno}")
    suspend fun getListOfQuotes(@Path("pageno") pageno: String):List<QuoteItemDto>
}

object ApiCons {
    const val BASE_URL = "https://api.quotable.io/"
}