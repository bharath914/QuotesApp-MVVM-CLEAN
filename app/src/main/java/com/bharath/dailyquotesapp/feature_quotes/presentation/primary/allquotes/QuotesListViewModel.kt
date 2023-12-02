package com.bharath.dailyquotesapp.feature_quotes.presentation.primary.allquotes


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toSavedEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.GetSearchQuotesListUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.DeleteFromSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.GetAllSavedQuoteIdsUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.InsertIntoSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.presentation.primary.homescreen.events.HomeEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesListViewModel @Inject constructor(
    private val repository: Repository,
    private val getAllSavedQuoteIdsUseCase: GetAllSavedQuoteIdsUseCase,
    private val deleteFromSavedQuotesUseCase: DeleteFromSavedQuotesUseCase,
    private val insertIntoSavedQuotesUseCase: InsertIntoSavedQuotesUseCase,
    private val listUseCase: GetSearchQuotesListUseCase,
) : ViewModel() {
    //    var listOfQuotes = Pager(
//        config = PagingConfig(pageSize = 20, prefetchDistance = 1, initialLoadSize = 20)
//    ) {
//        QuotesPagingSource(repository)
//    }.flow.cachedIn(viewModelScope)
    var listOfQuotes: Flow<PagingData<QuoteItem>> = emptyFlow()

    fun getList() {


        listOfQuotes = listUseCase()

    }


    val set: HashSet<String> = HashSet()


    fun getIds() {
        getAllSavedQuoteIdsUseCase().onEach {
            it.collectLatest { list ->
                set.addAll(list)
            }
        }.launchIn(viewModelScope)

    }


    fun onEvent(events: HomeEvents) {
        if (events is HomeEvents.ClickedOnFavButton) {
            if (events.saved) {
                viewModelScope.launch {
                    deleteFromSavedQuotesUseCase(events.quoteItem.toSavedEntity())
                }
            } else {
                viewModelScope.launch {
                    insertIntoSavedQuotesUseCase(events.quoteItem.toSavedEntity())
                }
            }
        }
    }


}