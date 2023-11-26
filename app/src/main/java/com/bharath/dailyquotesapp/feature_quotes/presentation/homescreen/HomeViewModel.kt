package com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen


import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.AuthorsPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.QuotesPagingSource
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.toQuoteItem
import com.bharath.dailyquotesapp.feature_quotes.data.other.Resource
import com.bharath.dailyquotesapp.feature_quotes.domain.datastore.DataStoreRepository
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.AuthorItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.QuoteItem
import com.bharath.dailyquotesapp.feature_quotes.domain.entity.toSavedEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.repository.Repository
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.GetQuotesListUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.GetRandomQuoteUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.DeleteFromSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.GetAllSavedQuoteIdsUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.GetQuoteUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.InsertIntoSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.events.HomeEvents
import com.bharath.dailyquotesapp.feature_quotes.presentation.homescreen.state.RandomQuoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getQuotesListUseCase: GetQuotesListUseCase,
    private val getRandomQuoteUseCase: GetRandomQuoteUseCase,
    private val repository: Repository,
    private val dataStoreRepository: DataStoreRepository,
    private val getLocalQuoteUseCase: GetQuoteUseCase,
    private val getAllSavedQuoteIdsUseCase: GetAllSavedQuoteIdsUseCase,
    private val insertIntoSavedQuotesUseCase: InsertIntoSavedQuotesUseCase,
    private val deleteFromSavedQuotesUseCase: DeleteFromSavedQuotesUseCase,


    ) : ViewModel() {

    private val _randomQuote = MutableStateFlow(RandomQuoteState())
    val randomQuote = _randomQuote.asStateFlow()


    fun manageDailyQuote(context: Context) {
        val dateToday = Calendar.getInstance().time.date.toString()

        viewModelScope.launch(IO) {

            dataStoreRepository.getLastSavedDate().collectLatest {

                if (it == "N/A") {
                    getRandomQuote()
                } else if (it == dateToday) {
                    getRandomQuoteFromLocal()

                } else {
                    getRandomQuote()
                }

            }

        }


    }

    private fun getRandomQuoteFromLocal() {
        getLocalQuoteUseCase()
            .onEach {
                it.collectLatest { entity ->

                    _randomQuote.tryEmit(
                        RandomQuoteState(
                            quoteItem = entity.toQuoteItem()
                        )
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun getRandomQuote() {

        getRandomQuoteUseCase().onEach { result ->

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





    fun onEvent(events: HomeEvents) {
        when (events) {
            is HomeEvents.ShowNavigationDrawer -> {

                _showNavBar.tryEmit(

                    true
                )


            }

            is HomeEvents.ClickedOnFavButton -> {
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

    val set: HashSet<String> = HashSet()


    fun getIds() {
        getAllSavedQuoteIdsUseCase().onEach {
            it.collectLatest { list ->
                set.addAll(list)
            }
        }.launchIn(viewModelScope)

    }

    private val _showNavBar = MutableStateFlow(false)
    val showNavBar = _showNavBar.asStateFlow()
    fun setNavBar(boolean: Boolean) {
        _showNavBar.tryEmit(boolean)
    }


    // authors


    fun getAuthors() {
//        authorsFlow =
    }
}


