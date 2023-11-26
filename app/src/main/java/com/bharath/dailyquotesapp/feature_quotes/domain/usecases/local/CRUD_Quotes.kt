package com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local

import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.QuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.LocalRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class InsertQuoteUseCase @Inject constructor(
    private val localRepo: LocalRepo,
) {
    suspend operator fun invoke(entity: QuoteEntity) {
        localRepo.insert(entity)
    }
}


class DeleteQuoteUseCase @Inject constructor(
    private val localRepo: LocalRepo,
) {
    suspend operator fun invoke(entity: QuoteEntity) {
        localRepo.delete(entity)
    }
}

class GetQuoteUseCase @Inject constructor(
    private val localRepo: LocalRepo,
) {


    operator fun invoke(): Flow<Flow<QuoteEntity>> = flow {


        try {
            val res = localRepo.getQuote()
            emit(res)

        } catch (e: Exception) {
            throw e
        }
    }

}