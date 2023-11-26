package com.bharath.dailyquotesapp.feature_quotes.domain.repository

import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.QuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import kotlinx.coroutines.flow.Flow

interface LocalRepo {


    /*
    Daily Quote
     */
    suspend fun insert(entity: QuoteEntity)
    suspend fun delete(entity: QuoteEntity)
    suspend fun getQuote(): Flow<QuoteEntity>

    /*
    Saved Quotes

     */

    suspend fun insertIntoSavedQuotes(savedQuoteEntity: SavedQuoteEntity)
    suspend fun deleteFromSavedQuotes(savedQuoteEntity: SavedQuoteEntity)
    suspend fun getAllQuotes(): Flow<List<SavedQuoteEntity>>

    suspend fun getAllIdsOfSavedQuotes(): Flow<List<String>>
}