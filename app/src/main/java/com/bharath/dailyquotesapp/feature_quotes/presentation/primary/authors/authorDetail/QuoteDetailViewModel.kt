package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.authors.authorDetail


import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.pagers.GetQuotesByTagPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.pagers.QuotesByAuthorPagingSource
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toSavedEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.DeleteFromSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.InsertIntoSavedQuotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuoteDetailViewModel @Inject constructor(

    savedStateHandle: SavedStateHandle,
    private val repository: Repository,
    private val insertIntoSavedQuotesUseCase: InsertIntoSavedQuotesUseCase,
    private val deleteFromSavedQuotesUseCase: DeleteFromSavedQuotesUseCase,
) : ViewModel() {


    var quotesFlow: Flow<PagingData<QuoteItem>>? = null


    fun getQuotesByAuthor(authorName: String) {

        quotesFlow = Pager(
            config = PagingConfig(20, initialLoadSize = 20, prefetchDistance = 1),
            pagingSourceFactory = {
                QuotesByAuthorPagingSource(repository, authorName)
            }
        ).flow.cachedIn(viewModelScope)


    }

    fun getQuotesByTag(tag: String) {
        quotesFlow = Pager(
            config = PagingConfig(20, initialLoadSize =20, prefetchDistance = 1),
            pagingSourceFactory = {
                GetQuotesByTagPagingSource(tag = tag, repository = repository)
            }
        ).flow.cachedIn(viewModelScope)
    }


    fun onEvent(event: AuthorDetailEvent) {
        when (event) {
            is AuthorDetailEvent.OnClickOnTabRow -> {
//                updateTabIndex(event.index)
            }

            is AuthorDetailEvent.onClickFav -> {
                viewModelScope.launch {

                    if (event.saved) {

                        deleteFromSavedQuotesUseCase(event.quoteItem.toSavedEntity())
                    } else {
                        insertIntoSavedQuotesUseCase(event.quoteItem.toSavedEntity())
                    }
                }
            }
        }
    }

}