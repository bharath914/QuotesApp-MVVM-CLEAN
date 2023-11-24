package com.bharath.dailyquotesapp.feature_quotes.domain.usecases

import com.bharath.dailyquotesapp.feature_quotes.data.other.Resource
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toDomain
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetQuotesListUseCase @Inject constructor(
    private val repository: Repository,
) {


    operator fun invoke(pageNo: String): Flow<Resource<List<QuoteItem>>> = flow {
        try {
            emit(Resource.Loading())
            val res = repository.getListOfQuotes(pageNo).map {
                it.toDomain()
            }
            emit(Resource.Success(data = res))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Http Exception"))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: "IO Exception"))
        }
    }

}