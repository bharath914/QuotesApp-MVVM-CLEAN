package com.bharath.dailyquotesapp.feature_quotes.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.QuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity

@Database(
    entities = [QuoteEntity::class, SavedQuoteEntity::class],
    version = 1
)
abstract class QuoteDataBase : RoomDatabase() {
    abstract val dao: QuoteDao
}