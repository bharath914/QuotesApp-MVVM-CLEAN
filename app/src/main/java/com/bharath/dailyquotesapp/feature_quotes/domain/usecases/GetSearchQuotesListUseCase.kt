package com.bharath.dailyquotesapp.feature_quotes.domain.usecases

import com.bharath.dailyquotesapp.feature_quotes.data.other.Resource
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSearchQuotesListUseCase @Inject constructor(
    private val repository: Repository,
) {


    operator fun invoke(query: String, pageNo: String): Flow<Resource<List<QuoteItem>>> = flow {



    }
}