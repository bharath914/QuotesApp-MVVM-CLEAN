package com.bharath.dailyquotesapp.feature_quotes.domain.usecases

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.SearchAuthorsPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.SearchQuotesPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.entity.ResultAuthor
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchQuoteUseCase @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(searchQuery: String): Flow<PagingData<QuoteItem>> = Pager(
        config = PagingConfig(20, prefetchDistance = 1, initialLoadSize = 20)
    ) {
        SearchQuotesPagingSource(repository, searchQuery)
    }.flow

}

class SearchAuthorsUseCase @Inject constructor(
    private val repository: Repository,
) {
    operator fun invoke(searchQuery: String): Flow<PagingData<ResultAuthor>> = Pager(
        config = PagingConfig(20, prefetchDistance = 1, initialLoadSize = 20)
    ) {
        SearchAuthorsPagingSource(searchQuery, repository)
    }.flow
}