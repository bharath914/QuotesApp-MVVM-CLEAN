package com.bharath.dailyquotesapp.feature_quotes.domain.usecases

import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.toQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.other.Resource
import com.bharath.dailyquotesapp.feature_quotes.domain.datastore.DataStoreRepository
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toDomain
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.LocalRepo
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.ui.theme.dark_colors
import com.bharath.dailyquotesapp.ui.theme.light_colors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar
import javax.inject.Inject



class GetRandomQuoteUseCase @Inject constructor(
    private val repository: Repository,
    private val localRepo: LocalRepo,
    private val dataStoreRepository: DataStoreRepository,

    ) {

    operator fun invoke(): Flow<Resource<QuoteItem>> = flow {
        try {
            emit(Resource.Loading())
            localRepo.deleteAllColumnsInRandomQuote()
            val res = repository.getRandomQuote()
            val date = Calendar.getInstance().time.date
            localRepo.insert(res.toQuoteEntity(date.toString()))
            dataStoreRepository.saveDate(date.toString())

            emit(
                Resource.Success(
                    data = res.toDomain(
                        light_colors.random(),
                        dark_colors.random(),

                    )
                )
            )


        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Http Exception"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "IO Exception"))
        }
    }

}