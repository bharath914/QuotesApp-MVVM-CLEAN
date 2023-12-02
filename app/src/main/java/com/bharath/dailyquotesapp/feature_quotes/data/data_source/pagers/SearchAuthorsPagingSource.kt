package com.bharath.dailyquotesapp.feature_quotes.data.data_source.pagers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.data.entity.ResultAuthor
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import javax.inject.Inject

class SearchAuthorsPagingSource @Inject constructor(

    private val searchQuery: String,
    private val repository: Repository,
) : PagingSource<Int, ResultAuthor>() {
    override fun getRefreshKey(state: PagingState<Int, ResultAuthor>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResultAuthor> {
        return try {
            val page = params.key ?: 1
            val res = repository.searchAuthors(page.toString(), searchQuery).results
            LoadResult.Page(
                data = res,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (res.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(
                e
            )
        }
    }
}