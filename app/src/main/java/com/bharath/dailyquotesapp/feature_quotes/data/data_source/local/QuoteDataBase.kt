package com.bharath.dailyquotesapp.feature_quotes.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.QuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.StringListConverter

@Database(
    entities = [QuoteEntity::class, SavedQuoteEntity::class],
    version = 2,

)
@TypeConverters(StringListConverter::class)
abstract class QuoteDataBase : RoomDatabase() {
    abstract val dao: QuoteDao
}