package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.QuotesPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.other.Resource
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.GetQuotesListUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.GetRandomNoteUseCase
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.state.RandomQuoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuotesListUseCase: GetQuotesListUseCase,
    private val getRandomNoteUseCase: GetRandomNoteUseCase,
    private val repository: Repository,
) : ViewModel() {

    private val _randomQuote = MutableStateFlow(RandomQuoteState())
    val randomQuote = _randomQuote.asStateFlow()


    fun getRandomQuote() {

        getRandomNoteUseCase().onEach { result ->

            when (result) {
                is Resource.Success -> {

                    _randomQuote.tryEmit(RandomQuoteState(quoteItem = result.data ?: QuoteItem()))

                }

                is Resource.Loading -> {
                    _randomQuote.tryEmit(
                        RandomQuoteState(isLoading = true)
                    )
                }

                else -> {
                    _randomQuote.tryEmit(
                        RandomQuoteState(error = result.message ?: "UNEXPECTED ERROR")
                    )
                }
            }

        }.launchIn(viewModelScope)
    }


    var listOfQuotes = emptyFlow<PagingData<QuoteItem>>()

    fun getPagingData() {


        listOfQuotes = Pager(
            config = PagingConfig(pageSize = 20, prefetchDistance = 1, initialLoadSize = 20)
        )
        {
            QuotesPagingSource(repository)
        }.flow
            .cachedIn(viewModelScope)
    }
}