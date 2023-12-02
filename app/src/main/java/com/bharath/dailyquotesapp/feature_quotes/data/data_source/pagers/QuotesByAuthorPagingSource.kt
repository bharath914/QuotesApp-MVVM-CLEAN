package com.bharath.dailyquotesapp.feature_quotes.data.data_source.pagers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toQuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.ui.theme.dark_colors
import com.bharath.dailyquotesapp.ui.theme.light_colors
import javax.inject.Inject

class QuotesByAuthorPagingSource @Inject constructor(
    private val repository: Repository,
    private val authorName: String,
) : PagingSource<Int, QuoteItem>() {
    override fun getRefreshKey(state: PagingState<Int, QuoteItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteItem> {
        return try {
            val nextPage = params.key ?: 1
            val quotes = repository.getQuotesByAuthor(nextPage.toString(), authorName)
            var idx = 0
            var idx2 = 0
            val darkcolors = dark_colors.shuffled()
            val lightcolors = light_colors.shuffled()

            LoadResult.Page(
                data = quotes.results.map {

                    if (idx >= quotes.results.size) {
                        idx = 0
                        idx2 = 0
                    }
                    it.toQuoteItem(
                        lightcolors[idx++],
                        darkcolors[idx2++]
                    )

                },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (quotes.results.isEmpty()) null else nextPage + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}