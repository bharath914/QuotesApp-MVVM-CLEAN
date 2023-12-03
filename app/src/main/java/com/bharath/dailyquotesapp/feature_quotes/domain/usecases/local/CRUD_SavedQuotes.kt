package com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local

import android.util.Log
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.LocalRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class InsertIntoSavedQuotesUseCase @Inject constructor(
    private val repository: LocalRepo,
) {
    suspend operator fun invoke(savedQuoteEntity: SavedQuoteEntity) {
        repository.insertIntoSavedQuotes(savedQuoteEntity)
    }
}

class DeleteFromSavedQuotesUseCase @Inject constructor(
    private val repository: LocalRepo,
) {
    suspend operator fun invoke(savedQuoteEntity: SavedQuoteEntity) {
        Log.d("Saved", "invoke: deleted ")
        repository.deleteFromSavedQuotes(savedQuoteEntity)
    }
}


class GetAllSavedQuoteIdsUseCase @Inject constructor(
    private val repository: LocalRepo,
) {
    operator fun invoke(): Flow<Flow<List<String>>> = flow {
        try {

            val res = repository.getAllIdsOfSavedQuotes()
            emit(res)

        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }
}

class GetAllSavedQuotesUseCase @Inject constructor(
    private val repository: LocalRepo,
) {
     operator fun invoke(): Flow<Flow<List<SavedQuoteEntity>>> =
        flow {

            try {

                val res = repository.getAllQuotes()
                emit(res)

            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }
}