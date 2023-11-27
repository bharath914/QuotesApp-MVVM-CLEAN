package com.bharath.dailyquotesapp.feature_quotes.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteDto
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import javax.inject.Inject

class SearchQuotesPagingSource @Inject constructor(
    private val repository: Repository,
) : PagingSource<Int, QuoteDto>() {
    override fun getRefreshKey(state: PagingState<Int, QuoteDto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteDto> {
        TODO("Not yet implemented")
    }

}