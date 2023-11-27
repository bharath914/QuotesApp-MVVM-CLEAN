package com.bharath.dailyquotesapp.feature_quotes.domain.repository

import androidx.paging.PagingData
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

    suspend fun deleteAllColumnsInRandomQuote()

    /*
    Saved Quotes

     */

    suspend fun insertIntoSavedQuotes(savedQuoteEntity: SavedQuoteEntity)
    suspend fun deleteFromSavedQuotes(savedQuoteEntity: SavedQuoteEntity)
    suspend fun getAllQuotes(): Flow<List<SavedQuoteEntity>>

    suspend fun getAllIdsOfSavedQuotes(): Flow<List<String>>

    suspend fun searchQuotes(searchQuery: String, limit: Int, offset: Int): List<SavedQuoteEntity>

    suspend fun getPagingOfSearchQuotes(userName:String): Flow<PagingData<SavedQuoteEntity>>

}