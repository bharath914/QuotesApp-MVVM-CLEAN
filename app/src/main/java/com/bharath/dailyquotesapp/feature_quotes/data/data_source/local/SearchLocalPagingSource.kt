package com.bharath.dailyquotesapp.feature_quotes.data.data_source.local

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.LocalRepo
import javax.inject.Inject

class SearchLocalPagingSource @Inject constructor(
    private val localRepo: LocalRepo,
    private val searchQuery: String,
) : PagingSource<Int, SavedQuoteEntity>() {
    override fun getRefreshKey(state: PagingState<Int, SavedQuoteEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SavedQuoteEntity> {
        return try {
            val page = params.key ?: 1
            val res = localRepo.searchQuotes(searchQuery, params.loadSize, page * params.loadSize)
            LoadResult.Page(
                data = res,
                prevKey = if (page == 1) null else page + 1,
                nextKey = if (res.isEmpty()) null else page + 1
            )


        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }
}