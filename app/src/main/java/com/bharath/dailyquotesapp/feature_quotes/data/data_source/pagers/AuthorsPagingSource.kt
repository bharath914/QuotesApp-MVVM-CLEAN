package com.bharath.dailyquotesapp.feature_quotes.data.data_source.pagers

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.AuthorItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toAuthorItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import javax.inject.Inject

class AuthorsPagingSource @Inject constructor(
    private val repository: Repository,
) : PagingSource<Int, AuthorItem>() {
    override fun getRefreshKey(state: PagingState<Int, AuthorItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AuthorItem> {
        return try {
            val nextPage = params.key ?: 1

            val authors = repository.getListOfAuthors(nextPage.toString(), "name")

            LoadResult.Page(
                data = authors.results.map {
                    it.toAuthorItem()
                },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (authors.results.isEmpty()) null else nextPage + 1
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}