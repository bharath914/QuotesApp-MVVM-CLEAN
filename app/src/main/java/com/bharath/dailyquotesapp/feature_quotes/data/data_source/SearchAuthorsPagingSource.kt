package com.bharath.dailyquotesapp.feature_quotes.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bharath.dailyquotesapp.feature_quotes.data.entity.AuthorsDto
import javax.inject.Inject

class SearchAuthorsPagingSource @Inject constructor(

) : PagingSource<Int, AuthorsDto>() {
    override fun getRefreshKey(state: PagingState<Int, AuthorsDto>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AuthorsDto> {
        TODO("Not yet implemented")
    }
}