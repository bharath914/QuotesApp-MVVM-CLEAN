package com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.QuoteDao
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.SearchLocalPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.QuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.LocalRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocalRepoImpl @Inject constructor(
    private val dao: QuoteDao,
) : LocalRepo {
    override suspend fun insert(entity: QuoteEntity) {
        withContext(IO) {
            dao.insert(entity)
        }
    }

    override suspend fun delete(entity: QuoteEntity) {
        withContext(IO) {
            dao.delete(entity)
        }
    }

    override suspend fun getQuote(): Flow<QuoteEntity> {
        return withContext(IO) {
            dao.getSavedQuote()
        }
    }

    override suspend fun deleteAllColumnsInRandomQuote() {
        return withContext(Dispatchers.IO) {
            dao.deleteAllSavedQuotes()
        }
    }

    override suspend fun insertIntoSavedQuotes(savedQuoteEntity: SavedQuoteEntity) {
        withContext(IO) {
            dao.insertIntoSavedQuotes(savedQuoteEntity)
        }
    }

    override suspend fun deleteFromSavedQuotes(savedQuoteEntity: SavedQuoteEntity) {
        withContext(IO) {
            dao.deleteFromSavedQuotes(savedQuoteEntity)
            Log.d(
                "Saved",
                "deleteFromSavedQuotes: ${savedQuoteEntity.idOfQuote} and ${savedQuoteEntity.content} "
            )
        }
    }

    override suspend fun getAllQuotes(): Flow<List<SavedQuoteEntity>> {
        return withContext(IO) {
            dao.getAllQuotes()
        }
    }

    override suspend fun getAllIdsOfSavedQuotes(): Flow<List<String>> {
        return withContext(IO) {
            dao.getAllIdsOfSavedQuotes()
        }
    }

    override suspend fun getPagingOfSearchQuotes(userName: String): Flow<PagingData<SavedQuoteEntity>> {
        val pagingSource = SearchLocalPagingSource(
            this,
            userName
        )
        return Pager(config = PagingConfig(20)) { pagingSource }.flow
    }

    override suspend fun searchQuotes(
        searchQuery: String,
        limit: Int,
        offset: Int,
    ): List<SavedQuoteEntity> {
        return withContext(Dispatchers.IO) {
            dao.searchSavedQuotes(searchQuery, offset, limit)
        }
    }
}