package com.bharath.dailyquotesapp.feature_quotes.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toDomain
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.ui.theme.dark_colors
import com.bharath.dailyquotesapp.ui.theme.light_colors
import javax.inject.Inject

class SearchQuotesPagingSource @Inject constructor(
    private val repository: Repository,
    private val query: String,
) : PagingSource<Int, QuoteItem>() {
    override fun getRefreshKey(state: PagingState<Int, QuoteItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteItem> {
        return try {
            val lighcolors = light_colors.shuffled()
            val darkcolors = dark_colors.shuffled()
            val page = params.key ?: 1
            var idx1 = 0
            var idx2 = 0
            val res = repository.searchQuotes(page.toString(), query).results.map {
                it.toDomain(lighcolors[idx1++], darkcolors[idx2++])
            }
            LoadResult.Page(
                data = res,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (res.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}