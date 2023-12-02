package com.bharath.dailyquotesapp.feature_quotes.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.pagers.QuotesPagingSource
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchQuotesListUseCase @Inject constructor(
    private val repository: Repository,
) {


    operator fun invoke(): Flow<PagingData<QuoteItem>> = Pager(
        config = PagingConfig(20, prefetchDistance = 1, initialLoadSize = 20)
    ) {
        QuotesPagingSource(repository)
    }.flow
}