package com.bharath.dailyquotesapp.feature_quotes.data.data_source.pagers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toQuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.ui.theme.dark_colors
import com.bharath.dailyquotesapp.ui.theme.light_colors
import javax.inject.Inject

class GetQuotesByTagPagingSource @Inject constructor(
    private val repository: Repository,
    private val tag: String,
) : PagingSource<Int, QuoteItem>() {
    override fun getRefreshKey(state: PagingState<Int, QuoteItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteItem> {
        return try {
            val page = params.key ?: 1
            val lightcolor = light_colors.shuffled()
            val darkColors = dark_colors.shuffled()
            val res = repository.getQuotesByTag(tag, pageNo = page.toString())
            LoadResult.Page(
                data = res.results.mapIndexed { index, result ->
                    result.toQuoteItem(lightcolor[index], darkColors[index])
                },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (res.results.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}