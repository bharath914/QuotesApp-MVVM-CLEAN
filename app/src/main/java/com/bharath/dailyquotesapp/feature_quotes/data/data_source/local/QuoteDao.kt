package com.bharath.dailyquotesapp.feature_quotes.data.data_source.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.QuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Insert
    suspend fun insert(quoteEntity: QuoteEntity)

    @Delete
    suspend fun delete(quoteEntity: QuoteEntity)

    @Query("SELECT * FROM Quote limit 1")
    fun getSavedQuote(): Flow<QuoteEntity>

    @Query("Delete  From Quote")
    suspend fun deleteAllSavedQuotes()


    @Insert(entity = SavedQuoteEntity::class)
    suspend fun insertIntoSavedQuotes(savedQuoteEntity: SavedQuoteEntity)

    @Delete(SavedQuoteEntity::class)
    suspend fun deleteFromSavedQuotes(savedQuoteEntity: SavedQuoteEntity)

    @Query("SELECT * From SavedQuotes")

    fun getAllQuotes(): Flow<List<SavedQuoteEntity>>

    @Query("SELECT idOfQuote from SavedQuotes")
    fun getAllIdsOfSavedQuotes(): Flow<List<String>>


    @Query("SELECT  * From SavedQuotes where content like :searchQuery or author like :searchQuery order by id DESC Limit :limit OFFSET :offset")
    fun searchSavedQuotes(
        searchQuery: String,
        offset: Int,
        limit: Int,
    ): List<SavedQuoteEntity>

}