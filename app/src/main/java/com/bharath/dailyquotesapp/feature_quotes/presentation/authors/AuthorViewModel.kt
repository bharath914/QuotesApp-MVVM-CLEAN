package com.bharath.dailyquotesapp.feature_quotes.presentation.authors


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.AuthorsPagingSource
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    var authorsFlow = Pager(
        config = PagingConfig(20, 1, initialLoadSize = 20),
        pagingSourceFactory = {
            AuthorsPagingSource(repository)
        }
    ).flow.cachedIn(viewModelScope)

}