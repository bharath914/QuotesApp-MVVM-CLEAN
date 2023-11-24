package com.bharath.dailyquotesapp.feature_quotes.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.data.entity.QuoteItemDto
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toDomain
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import javax.inject.Inject

class QuotesPagingSource @Inject constructor(
    private val repository: Repository,
) : PagingSource<Int, QuoteItem>() {
    override fun getRefreshKey(state: PagingState<Int, QuoteItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteItem> {
        return try {
            val nextPage = params.key ?: 1
            val quotes = repository.getListOfQuotes(pageNo = "$nextPage").map {
                it.toDomain()
            }
            LoadResult.Page(
                data = quotes,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (quotes.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }

}