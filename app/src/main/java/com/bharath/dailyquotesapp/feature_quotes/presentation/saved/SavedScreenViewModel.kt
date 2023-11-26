package com.bharath.dailyquotesapp.feature_quotes.presentation.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bharath.dailyquotesapp.feature_quotes.data.data_source.local.entity.SavedQuoteEntity
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.DeleteFromSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.GetAllSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.domain.usecases.local.InsertIntoSavedQuotesUseCase
import com.bharath.dailyquotesapp.feature_quotes.presentation.saved.events.SavedScreenEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedScreenViewModel @Inject constructor(

    private val deleteUseCase: DeleteFromSavedQuotesUseCase,
    private val insertUseCase: InsertIntoSavedQuotesUseCase,
    private val getAllSavedUseCase: GetAllSavedQuotesUseCase,

    ) : ViewModel() {

    private val _savedItems = MutableStateFlow(emptyList<SavedQuoteEntity>())
    val savedItems = _savedItems.asStateFlow()


    fun getAllQuotes() {


        getAllSavedUseCase().onEach {
            it.collectLatest { ent ->

                _savedItems.tryEmit(ent)
            }
        }.launchIn(viewModelScope)

    }


    private var recentlyDeleted:SavedQuoteEntity ?= null
    fun onEvent(events: SavedScreenEvents) {
        when (events) {
            is SavedScreenEvents.clickOnDelete -> {

                recentlyDeleted = events.savedQuoteEntity
                viewModelScope.launch(IO) {
                    deleteUseCase(events.savedQuoteEntity)
                }
            }
            is SavedScreenEvents.undoDelete ->{
                recentlyDeleted?.let {


                    viewModelScope.launch(IO) {
                        insertUseCase(it)
                    }
                }
            }
        }
    }

}