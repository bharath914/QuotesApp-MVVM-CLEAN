package com.bharath.dailyquotesapp.feature_quotes.presentation.search


import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.pagers.SearchQuotesPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.data.entity.ResultAuthor
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.SearchAuthorsUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.SearchQuoteUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.LocalSearchUseCase
import com.bharath.dailyquotesapp.feature_quotes.presentation.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchQuoteUseCase: SearchQuoteUseCase,
    private val searchAuthorsUseCase: SearchAuthorsUseCase,
    private val localSearchUseCase: LocalSearchUseCase,
    private val repository: Repository,
    savedStateHandle: SavedStateHandle,

    ) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()
    fun emitSearchText(str: String) {
        _searchText.tryEmit(str)
    }


    private val _searchEvent = MutableStateFlow(SearchEvent.clickedOnEnter)
    val searchEvent = _searchEvent.asStateFlow()
    val route = mutableStateOf(Screens.AllQuotes.route)

    init {
        savedStateHandle.get<String>("route")?.let {
            route.value = it
        }
    }


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.clickedOnEnter -> {

                if (route.value == Screens.AuthorScreen.route) {
                    Log.d("EnterClick", "onEvent:${route.value} ")
                    searchAuthors()
                } else if (route.value == Screens.Saved.route) {
                    Log.d("EnterClick", "onEvent:${route.value} ")
                    searchSavedItems()
                } else {
                    Log.d("EnterClick", "onEvent:${route.value} ")
                    searchQuotes()
                }

            }

            is SearchEvent.onSearchText -> {
                _searchText.tryEmit(event.string)
            }
        }


    }

    fun onEnterClick() {
        when (route.value) {
            Screens.AllQuotes.route -> {
                viewModelScope.launch(IO) {

                searchQuotes()
                }
            }

            Screens.AuthorScreen.route -> {
                viewModelScope.launch(IO) {

                    searchAuthors()
                }            }

            Screens.Saved.route -> {
                viewModelScope.launch(IO) {

                    searchSavedItems()
                }            }
        }
    }


    var authorsFlow = emptyFlow<PagingData<ResultAuthor>>()
    var savedItemFlow = emptyFlow<PagingData<SavedQuoteEntity>>()
    var quotesFlow = emptyFlow<PagingData<QuoteItem>>()

    private fun searchAuthors() {


        authorsFlow = searchAuthorsUseCase(searchText.value)

    }

    private fun searchSavedItems() {
        savedItemFlow = localSearchUseCase(_searchText.value)
    }

     fun searchQuotes() {

        quotesFlow = Pager(
            config = PagingConfig(20, prefetchDistance = 1, initialLoadSize = 20)
        ) {
            SearchQuotesPagingSource(repository, _searchText.value)
        }.flow
    }


}
