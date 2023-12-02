package com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.SearchLocalPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.LocalRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSearchUseCase @Inject constructor(
    private val localRepo: LocalRepo,
) {

    operator fun invoke(searchText: String): Flow<PagingData<SavedQuoteEntity>> = Pager(
        config = PagingConfig(20, prefetchDistance = 1, initialLoadSize = 20)
    ) {
        SearchLocalPagingSource(localRepo, searchText)
    }.flow
}